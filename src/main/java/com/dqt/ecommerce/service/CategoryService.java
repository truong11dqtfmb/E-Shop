package com.dqt.ecommerce.service;

import com.dqt.ecommerce.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Category save(Category category);

    Category update(Category category,Long id);

    Optional<Category> findById(Long id);

    Category deleteById(Long id);

    Category enabledById(Long id);

    int countCategory();

    void deleteAllCategory();

    void enabledAllCategory();


//======================================================================================================================

    List<Category> findAllActived();


}
