package com.quochuy.banhangcoban.controller.api;

import com.quochuy.banhangcoban.exception.DataInputException;
import com.quochuy.banhangcoban.model.Cart;
import com.quochuy.banhangcoban.model.CartItem;
import com.quochuy.banhangcoban.model.Product;
import com.quochuy.banhangcoban.model.dto.CartItemDTO;
import com.quochuy.banhangcoban.service.bill.IBillService;
import com.quochuy.banhangcoban.service.billDetail.IBillDetailService;
import com.quochuy.banhangcoban.service.cart.ICartService;
import com.quochuy.banhangcoban.service.cartItem.ICartItemService;
import com.quochuy.banhangcoban.service.product.IProductService;
import com.quochuy.banhangcoban.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartAPIController {
    @Autowired
    private IProductService productService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private IBillService billService;

    @Autowired
    private IBillDetailService billDetailService;

    @Autowired
    private AppUtil appUtils;


    @PostMapping("/add/{tableId}")
    public ResponseEntity<?> addCart(@PathVariable long tableId, @RequestBody CartItemDTO cartItemDTO) {

        Long productId = cartItemDTO.getProductId();

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()) {
            throw new DataInputException("Sản phẩm không tồn tại");
        }

        Product product = productOptional.get();

        Optional<Cart> cartOptional = cartService.findByTable(tableId);

        Cart cart = new Cart();

        if (!cartOptional.isPresent()) {

            cart.setTotalAmount(product.getPrice());
            cart.setTableId(tableId);

            Cart newCart = cartService.save(cart);

            cart = newCart;

            CartItem cartItem = new CartItem();
            cartItem.setId(0L);
            cartItem.setCart(newCart);
            cartItem.setProduct(product);
            cartItem.setProductName(product.getProductName());
            cartItem.setProductPrice(product.getPrice());
            cartItem.setQuantity(1L);
            cartItem.setAmount(product.getPrice());

            cartItemService.save(cartItem);
        }
        else {
            cart = cartOptional.get();

            Optional<CartItem> cartItemOptional = cartItemService.findByCartAndProduct(cart, product);

            if (!cartItemOptional.isPresent()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(0L);
                cartItem.setCart(cartOptional.get());
                cartItem.setProduct(product);
                cartItem.setProductName(product.getProductName());
                cartItem.setProductPrice(product.getPrice());
                cartItem.setQuantity(1L);
                cartItem.setAmount(product.getPrice());

                cartItemService.save(cartItem);
            }
            else {
                CartItem cartItem = cartItemOptional.get();

                long oldQuantity = cartItem.getQuantity();
                long newQuantity = oldQuantity + 1;
                BigDecimal newPrice = product.getPrice();
                BigDecimal newAmount = newPrice.multiply(new BigDecimal(newQuantity));

                cartItem.setProductName(product.getProductName());
                cartItem.setProductPrice(newPrice);
                cartItem.setQuantity(newQuantity);
                cartItem.setAmount(newAmount);
                cartItemService.save(cartItem);
            }

            BigDecimal bAmount = cartItemService.getSumAmount(cart.getId());

            cart.setTotalAmount(bAmount);

            cartService.save(cart);
        }

        long countQuantity = cartItemService.countCartItemByCart(cart);

        Map<String, Long> results = new HashMap<>();
        results.put("totalCartItemQuantity", countQuantity);

        return new ResponseEntity<>(results, HttpStatus.CREATED);

    }


    @PostMapping("/checkout/{tableId}")
    public ResponseEntity<?> checkout(@PathVariable long tableId) {

        try {
            boolean success = cartService.checkout(tableId);

            if (success) {
                return new ResponseEntity<>(HttpStatus.OK);
            }

            throw new DataInputException("Liên hệ với quản lý.");

        } catch (Exception e) {
            throw new DataInputException("Liên hệ với quản lý.");
        }
    }
}
