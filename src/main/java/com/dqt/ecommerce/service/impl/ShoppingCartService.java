package com.dqt.ecommerce.service.impl;

import com.dqt.ecommerce.model.CartItem;
import com.dqt.ecommerce.model.Product;
import com.dqt.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShoppingCartService {
    @Autowired
    private ProductService productService;

    Map<Long, CartItem> maps = new HashMap<>();

    public void add(CartItem item) {
        CartItem cartItem = maps.get(item.getProduct().getId());
        if (cartItem == null) {
            maps.put(item.getProduct().getId(), item);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItem.setPrice(item.getProduct().getCost() * cartItem.getQuantity());
        }
    }

    public void remove(long id) {
        maps.remove(id);
        int size = maps.size();
    }

    public CartItem update(long proId, int qty) {
        Product product = productService.findById(proId).get();
        CartItem item = maps.get(proId);
        item.setQuantity(qty);
        item.setPrice(product.getCost() * qty);
        return item;
    }

    public void clear() {
        maps.clear();
    }

    public Collection<CartItem> getAllItems() {
        return maps.values();
    }

    public int getCount() {
        return maps.values().size();
    }

    public long getAmount() {
        return maps.values().stream().mapToLong(item -> item.getQuantity() * item.getProduct().getCost()).sum();
    }


}
