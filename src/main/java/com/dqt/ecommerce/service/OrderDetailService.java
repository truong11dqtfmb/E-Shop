package com.dqt.ecommerce.service;

import com.dqt.ecommerce.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> findAllByOrderId(long id);
}
