package com.dqt.ecommerce.repository;

import com.dqt.ecommerce.model.Category;
import com.dqt.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
