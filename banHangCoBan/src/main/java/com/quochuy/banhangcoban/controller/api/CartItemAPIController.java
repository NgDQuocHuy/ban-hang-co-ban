package com.quochuy.banhangcoban.controller.api;

import com.quochuy.banhangcoban.exception.DataInputException;
import com.quochuy.banhangcoban.model.Cart;
import com.quochuy.banhangcoban.model.CartItem;
import com.quochuy.banhangcoban.model.Product;
import com.quochuy.banhangcoban.service.cart.ICartService;
import com.quochuy.banhangcoban.service.cartItem.ICartItemService;
import com.quochuy.banhangcoban.service.product.IProductService;
import com.quochuy.banhangcoban.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemAPIController {
    @Autowired
    private AppUtil appUtils;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private IProductService productService;

    @GetMapping("/{tableId}")
    public ResponseEntity<?> getCartItems(@PathVariable long tableId) {

        Optional<Cart> cartOptional = cartService.findByTable(tableId);

        if (!cartOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Cart cart = cartOptional.get();

        List<CartItem> cartItem = cartItemService.findByCart(cart);

        if (cartItem.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PatchMapping("/add/{cartItemId}")
    public ResponseEntity<?> addCartItem(@PathVariable long cartItemId) {

        Optional<CartItem> cartItemOptional = cartItemService.findById(cartItemId);

//        if (!cartItemOptional.isPresent()) {
//            throw new DataInputException("Sản phẩm không hợp lệ");
//        }

        CartItem cartItem = cartItemOptional.get();

        Product product = cartItem.getProduct();

        BigDecimal newPrice = product.getPrice();
        long currentQuantity = cartItem.getQuantity();
        long newQuantity = currentQuantity + 1;

        BigDecimal newAmount = newPrice.multiply(new BigDecimal(newQuantity));

        cartItem.setProductPrice(newPrice);
        cartItem.setQuantity(newQuantity);
        cartItem.setAmount(newAmount);

        CartItem newCartItem = cartItemService.save(cartItem);

        Cart cart = cartItem.getCart();

        BigDecimal bAmount = cartItemService.getSumAmount(cart.getId());

        cart.setTotalAmount(bAmount);

        cartService.save(cart);

        long totalCartItemQuantity = cartItemService.countCartItemByCart(cartItem.getCart());

        return new ResponseEntity<>(newCartItem, HttpStatus.OK);
    }

    @PatchMapping("/minus/{cartItemId}")
    public ResponseEntity<?> minusCartItem(@PathVariable long cartItemId) {

        Optional<CartItem> cartItemOptional = cartItemService.findById(cartItemId);

        if (!cartItemOptional.isPresent()) {
            throw new DataInputException("Sản phẩm không hợp lệ");
        }

        CartItem cartItem = cartItemOptional.get();

        Product product = cartItem.getProduct();

        BigDecimal newPrice = product.getPrice();
        long currentQuantity = cartItem.getQuantity();
        long newQuantity = currentQuantity - 1;

        BigDecimal newAmount = newPrice.multiply(new BigDecimal(newQuantity));

        cartItem.setProductPrice(newPrice);
        cartItem.setQuantity(newQuantity);
        cartItem.setAmount(newAmount);

        CartItem newCartItem = cartItemService.save(cartItem);

        Cart cart = cartItem.getCart();

        BigDecimal bAmount = cartItemService.getSumAmount(cart.getId());

        cart.setTotalAmount(bAmount);

        Cart newCart = cartService.save(cart);
        return new ResponseEntity<>(newCart, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable long cartItemId) {

        Optional<CartItem> cartItemOptional = cartItemService.findById(cartItemId);

        if (!cartItemOptional.isPresent()) {
            throw new DataInputException("Sản phẩm không hợp lệ");
        }

        cartItemService.remove(cartItemId);

        Cart cart = cartItemOptional.get().getCart();
        long id = cart.getId();
        System.out.println(cart.getId());
        System.out.println(cart);
        BigDecimal bAmount = cartItemService.getSumAmount(cart.getId());
        if (bAmount == null) {
            cartService.delete(cart);
        }
        else {
            cart.setTotalAmount(bAmount);
            cartService.save(cart);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/amount/{cartId}")
    public String getTotalAmountByCartId(@PathVariable long cartId) {

        BigDecimal bAmount = cartItemService.getSumAmount(cartId);

        String amount = bAmount.toString();

        return amount;
    }

}
