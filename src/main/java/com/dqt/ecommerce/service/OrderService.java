package com.dqt.ecommerce.service;

import com.dqt.ecommerce.dto.OrderDTO;
import com.dqt.ecommerce.model.CartItem;
import com.dqt.ecommerce.model.Order;
import com.dqt.ecommerce.model.Order;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order save(String email,OrderDTO orderDTO);

    void saveOrderDetail(long orderId, CartItem cartItem);

    List<Order> findAll();

    Order findById(long id);

    Page<Order> pageFindAll(int pageNumber, String sortField, String sortDir);

    long countOrder();
}
