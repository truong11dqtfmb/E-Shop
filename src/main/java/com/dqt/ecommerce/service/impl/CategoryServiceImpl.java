package com.dqt.ecommerce.service.impl;

import com.dqt.ecommerce.model.Category;
import com.dqt.ecommerce.repository.CategoryRepository;
import com.dqt.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

//======================================================================================================================

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        category.setActived(true);
        category.setDeleted(false);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category, Long id) {
        Optional<Category> cate = findById(id);
        if (cate.isPresent()) {
            cate.get().setName(category.getName());
            categoryRepository.save(cate.get());
            return cate.get();
        }
        return category;
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category deleteById(Long id) {
        Optional<Category> category = findById(id);
        if (category.isPresent()) {
            category.get().setDeleted(true);
            category.get().setActived(false);
            categoryRepository.save(category.get());
            return category.get();
        }
        return null;
    }

    @Override
    public Category enabledById(Long id) {
        Optional<Category> category = findById(id);
        if (category.isPresent()) {
            category.get().setDeleted(false);
            category.get().setActived(true);
            categoryRepository.save(category.get());
            return category.get();
        }
        return null;
    }

    @Override
    public int countCategory() {
        return (int) categoryRepository.count();
    }

    @Override
    public void deleteAllCategory() {
        categoryRepository.findAll().forEach(category -> {
            category.setActived(false);
            category.setDeleted(true);
            categoryRepository.save(category);
        });
    }

    @Override
    public void enabledAllCategory() {
        categoryRepository.findAll().forEach(category -> {
            category.setActived(true);
            category.setDeleted(false);
            categoryRepository.save(category);
        });
    }


//======================================================================================================================
    @Override
    public List<Category> findAllActived() {
        return categoryRepository.findByIsActivedTrueAndIsDeletedFalse();
    }


}
