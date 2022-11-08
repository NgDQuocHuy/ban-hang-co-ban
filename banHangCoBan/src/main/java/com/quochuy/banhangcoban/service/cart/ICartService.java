package com.quochuy.banhangcoban.service.cart;

import com.quochuy.banhangcoban.model.Cart;
import com.quochuy.banhangcoban.service.IGeneralService;

import java.util.Optional;

public interface ICartService extends IGeneralService<Cart> {

    Optional<Cart> findByTable(long table);
    boolean checkout(long table);

    public void delete(Cart cart);
}
