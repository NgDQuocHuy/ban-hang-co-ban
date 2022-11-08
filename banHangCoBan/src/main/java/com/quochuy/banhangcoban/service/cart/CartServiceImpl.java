package com.quochuy.banhangcoban.service.cart;

import com.quochuy.banhangcoban.exception.DataInputException;
import com.quochuy.banhangcoban.model.Bill;
import com.quochuy.banhangcoban.model.BillDetail;
import com.quochuy.banhangcoban.model.Cart;
import com.quochuy.banhangcoban.model.CartItem;
import com.quochuy.banhangcoban.repository.BillDetailRepository;
import com.quochuy.banhangcoban.repository.BillRepository;
import com.quochuy.banhangcoban.repository.CartItemRepository;
import com.quochuy.banhangcoban.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements ICartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Cart getById(Long id) {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void remove(Long id) {
        cartRepository.deleteById(id);
    }

    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    @Override
    public Optional<Cart> findByTable(long table) {
        return cartRepository.findByTableId(table);
    }

    @Override
    public boolean checkout(long table) {
        boolean success = false;
        try {
            Optional<Cart> cartOptional = cartRepository.findByTableId(table);

            if (!cartOptional.isPresent()) {
                throw new DataInputException("Số bàn không hợp lệ.");
            }

            Cart cart = cartOptional.get();

            Bill bill = cart.toBill();
            Bill newBill = billRepository.save(bill);

            List<CartItem> cartItems = cartItemRepository.findByCart(cart);

            for (CartItem item : cartItems) {
                BillDetail billDetail = item.toBillDetail(newBill);

                billDetailRepository.save(billDetail);

                cartItemRepository.deleteById(item.getId());
            }

            cartRepository.deleteById(cart.getId());
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

}
