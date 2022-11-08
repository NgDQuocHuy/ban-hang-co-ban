package com.quochuy.banhangcoban.service.cartItem;

import com.quochuy.banhangcoban.model.Cart;
import com.quochuy.banhangcoban.model.CartItem;
import com.quochuy.banhangcoban.model.Product;
import com.quochuy.banhangcoban.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartItemServiceImpl implements ICartItemService{
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> findAll() {
        return null;
    }

    @Override
    public CartItem getById(Long id) {
        return null;
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public List<CartItem> findByCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }

    @Override
    public Optional<CartItem> findByCartAndProduct(Cart cart, Product product) {
        return cartItemRepository.findByCartAndProduct(cart, product);
    }

    @Override
    public long countCartItemByCart(Cart cart) {
        return cartItemRepository.countCartItemByCart(cart);
    }

    @Override
    public BigDecimal getSumAmount(long cartId) {
        return cartItemRepository.getSumAmount(cartId);
    }

    @Override
    public long countCartItemByCartId(long cartId) {
        return cartItemRepository.countCartItemByCartId(cartId);
    }

    @Override
    public Boolean existsByProduct(Product product) {
        return cartItemRepository.existsByProduct(product);
    }

    @Override
    public Optional<CartItem> findByProduct(Product product) {
        return cartItemRepository.findByProduct(product);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void remove(Long id) {
        cartItemRepository.deleteById(id);
    }
}