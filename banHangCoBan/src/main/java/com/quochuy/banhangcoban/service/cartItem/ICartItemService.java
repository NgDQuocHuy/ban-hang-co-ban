package com.quochuy.banhangcoban.service.cartItem;

import com.quochuy.banhangcoban.model.Cart;
import com.quochuy.banhangcoban.model.CartItem;
import com.quochuy.banhangcoban.model.Product;
import com.quochuy.banhangcoban.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICartItemService extends IGeneralService<CartItem> {
    Boolean existsByProduct(Product product);

    Optional<CartItem> findByProduct(Product product);

    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    long countCartItemByCart(Cart cart);

    long countCartItemByCartId(long cartId);

    BigDecimal getSumAmount(@Param("cartId") long cartId);

}
