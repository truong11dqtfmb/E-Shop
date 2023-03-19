package com.dqt.ecommerce.repository;

import com.dqt.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByIsActivedTrueAndIsDeletedFalse();
//    Category findByIdAndIsActivedTrueAndIsDeletedFalse(int id);
}
