package com.dqt.ecommerce.controller.customer;

import com.dqt.ecommerce.dto.ReadFileDTO;
import com.dqt.ecommerce.model.Product;
import com.dqt.ecommerce.service.impl.ShoppingCartService;
import com.dqt.ecommerce.service.CategoryService;
import com.dqt.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shop")
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ShoppingCartService cartService;


    @GetMapping({"","/","/home"})
    public String home(Model model){
        model.addAttribute("products",productService.findTop8());

        return "customer/index";
    }

    @GetMapping("/contact")
    public String contact(Model model){

        return "customer/contact";
    }

//    @GetMapping("/products")
//    public String products(Model model){
//        model.addAttribute("products",productService.findAllActived());
//
//        return "customer/product";
//    }
    //    Page & Sort
    @GetMapping("/products")
    public String findAllProduct(Model model) {
        return page(model, 1, "id", "asc");
    }

    @GetMapping(value = {"/products/page/{pageNumber}"})
    String page(Model model, @PathVariable(name = "pageNumber") int currentPage,
                @RequestParam("sortField") String sortField,
                @RequestParam("sortDir") String sortDir) {
        Page<Product> page = productService.pagefindAllActived(currentPage, sortField, sortDir);
        long totalItem = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("totalItem", totalItem);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("products", page.getContent());
        return "customer/product";
    }

    //    Search
    @GetMapping("/products/search")
    public String searchProduct(Model model, @RequestParam("search") String search) {
        return pageSearch(model, 1, "id", "asc", search);
    }

    @GetMapping(value = {"/products/search/{pageNumber}"})
    String pageSearch(Model model, @PathVariable(name = "pageNumber") int currentPage,
                      @RequestParam("sortField") String sortField,
                      @RequestParam("sortDir") String sortDir,
                      @RequestParam("search") String search) {
        Page<Product> page = productService.pageSearchActived(search, currentPage, sortField, sortDir);
        long totalItem = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("search", search);
        model.addAttribute("totalItem", totalItem);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("products", page.getContent());
        return "customer/product_search";
    }



    @GetMapping("/category/{id}")
    public String category(@PathVariable("id")Long id, Model model){
        model.addAttribute("category",categoryService.findById(id).get());
        model.addAttribute("products",productService.findAllByCategoryId(id));
        return "customer/category";
    }

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable("id")Long id, Model model){
        long categoryId = productService.findById(id).get().getCategory().getId();
        model.addAttribute("productRelative", productService.findAllByCategoryId(categoryId));
        model.addAttribute("product",productService.findById(id).get());
        return "customer/productDetail";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("categories",categoryService.findAllActived());
        model.addAttribute("cartCount", cartService.getCount());
    }


}
