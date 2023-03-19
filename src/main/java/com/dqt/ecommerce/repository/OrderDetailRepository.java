package com.dqt.ecommerce.repository;

import com.dqt.ecommerce.model.Category;
import com.dqt.ecommerce.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findAllByOrderId(long id);
}
