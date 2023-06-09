package com.dqt.ecommerce.controller.admin;

import com.dqt.ecommerce.model.Category;
import com.dqt.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

//  Find All Category is Active
    @GetMapping("/categories/actived")
    public String findAllCategoryActived(Model model) {
        model.addAttribute("categories", categoryService.findAllActived());

        return "admin/categories";
    }

//  Find All Category
    @GetMapping("/categories")
    public String findAllCategory(Model model) {
        model.addAttribute("categories", categoryService.findAll());

        return "admin/categories";
    }

//  Form new Category
    @GetMapping("/category/addForm")
    public String saveCategory(Model model) {
        model.addAttribute("category", new Category());

        return "admin/category_add";
    }

//  Save Category
    @PostMapping("/category/save")
    public String createCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/category_add";
        }
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

//  Form edit Category
    @GetMapping("/category/editForm/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "admin/category_edit";
        }
        return "redirect:/admin/categories";
    }

//  Update Category
    @PostMapping("/category/update/{id}")
    public String updateCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, @PathVariable("id") Long id, Model model) {
        if (result.hasErrors()) {
            return "admin/category_edit";
        }
        categoryService.update(category, id);
        return "redirect:/admin/categories";
    }

//  Delete Category
    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, Model model) {
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }

//  Enable Category
    @GetMapping("/category/enabled/{id}")
    public String enabledCategory(@PathVariable("id") Long id, Model model) {
        categoryService.enabledById(id);
        return "redirect:/admin/categories";
    }

//  Delete All Category
    @GetMapping("/categories/deleteAll")
    public String deleteAll(Model model) {
        categoryService.deleteAllCategory();
        return "redirect:/admin/categories";
    }

//  Enable All Category
    @GetMapping("/categories/enableAll")
    public String enableAll(Model model) {
        categoryService.enabledAllCategory();
        return "redirect:/admin/categories";
    }

}
