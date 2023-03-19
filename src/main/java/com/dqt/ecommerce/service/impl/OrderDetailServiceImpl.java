package com.dqt.ecommerce.service.impl;

import com.dqt.ecommerce.model.OrderDetail;
import com.dqt.ecommerce.repository.OrderDetailRepository;
import com.dqt.ecommerce.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Override
    public List<OrderDetail> findAllByOrderId(long id) {
        return orderDetailRepository.findAllByOrderId(id);
    }
}
