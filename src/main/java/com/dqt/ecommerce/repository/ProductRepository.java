package com.dqt.ecommerce.repository;

import com.dqt.ecommerce.model.Category;
import com.dqt.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByNameContaining(String name);

    Page<Product> findAll(Pageable page);

    @Query(value = "select * from product where name like %?1% or title like %?1%",
            nativeQuery = true)
    Page<Product> search(String search, Pageable pageable);



    Page<Product> findAllByIsActivedTrueAndIsDeletedFalse(Pageable page);

    @Query(value = "select * from product where is_actived = true and is_deleted = false and name like %?1% or title like %?1%",
            nativeQuery = true)
    Page<Product> searchAllActived(String search, Pageable pageable);

    List<Product> findByIsActivedTrueAndIsDeletedFalse();

    List<Product> findByCategoryId(long id);

    @Query(value = "SELECT * FROM product p WHERE p.is_actived = true and p.is_deleted = false ORDER BY id DESC LIMIT 8", nativeQuery = true)
    List<Product> findByTop8();

}
