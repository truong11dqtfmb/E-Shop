package com.dqt.ecommerce.service.impl;

import com.dqt.ecommerce.constant.SystemConstant;
import com.dqt.ecommerce.dto.OrderDTO;
import com.dqt.ecommerce.model.CartItem;
import com.dqt.ecommerce.model.Order;
import com.dqt.ecommerce.model.OrderDetail;
import com.dqt.ecommerce.model.User;
import com.dqt.ecommerce.repository.OrderDetailRepository;
import com.dqt.ecommerce.repository.OrderRepository;
import com.dqt.ecommerce.service.OrderService;
import com.dqt.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public Order save(String email,OrderDTO dto) {
        User user = userService.findByEmail(email);

        Order order = new Order();
        order.setCus_fullName(dto.getFullName());
        order.setCus_phone(dto.getPhone());
        order.setCus_address(dto.getAddress());
        order.setCus_email(dto.getEmail());
        order.setCus_note(dto.getNote());
        order.setPayments(dto.getPayments());
        order.setUser(user);
        order.setCreateDate(new Date());

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String orderCode = "No_" + currentDateTime;

        System.out.println(orderCode);

        order.setOrderCode(orderCode);
        order.setStatus(0);

        return orderRepository.save(order);
    }

    @Override
    public void saveOrderDetail(long orderId, CartItem cartItem) {
        Order order = orderRepository.findById(orderId).get();

        OrderDetail detail = new OrderDetail();
        detail.setOrder(order);
        detail.setProduct(cartItem.getProduct());
        detail.setQuantity(cartItem.getQuantity());
        detail.setAmount(cartItem.getProduct().getCost() * cartItem.getQuantity());

        orderDetailRepository.save(detail);

    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public Page<Order> pageFindAll(int pageNumber, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, SystemConstant.PAGE_SIZE, sort);

        return orderRepository.findAll(pageable);
    }

    @Override
    public long countOrder() {
        return orderRepository.count();
    }


}
