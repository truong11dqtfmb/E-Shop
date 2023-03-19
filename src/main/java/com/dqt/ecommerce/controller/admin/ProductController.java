package com.dqt.ecommerce.controller.admin;

import com.dqt.ecommerce.dto.ProductDTO;
import com.dqt.ecommerce.dto.ReadFileDTO;
import com.dqt.ecommerce.import_export.ProductExportExcel;
import com.dqt.ecommerce.import_export.ProductImportExcel;
import com.dqt.ecommerce.model.Product;
import com.dqt.ecommerce.service.CategoryService;
import com.dqt.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    ProductImportExcel productImportExcel;

    //    Page & Sort
    @GetMapping("/products")
    public String findAllProduct(Model model) {
        return page(model, 1, "id", "asc");
    }

    @GetMapping(value = {"/products/page/{pageNumber}"})
    String page(Model model, @PathVariable(name = "pageNumber") int currentPage,
                @RequestParam("sortField") String sortField,
                @RequestParam("sortDir") String sortDir) {
        Page<Product> page = productService.pagefindAll(currentPage, sortField, sortDir);
        long totalItem = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("totalItem", totalItem);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("readFileDTO", new ReadFileDTO());
        model.addAttribute("products", page.getContent());
        return "admin/products";
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
        Page<Product> page = productService.pageSearch(search, currentPage, sortField, sortDir);
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
        return "admin/products_search";
    }

    @GetMapping("/product/addForm")
    public String saveProduct(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product_add";
    }

    @PostMapping("/product/save")
    public String createProduct(@Valid @ModelAttribute("productDTO") ProductDTO productDTO, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            System.out.println("failed");
            return "admin/product_add";
        }
        System.out.println("oke");
        productService.save(productDTO);
        return "redirect:/admin/products";
    }

    @GetMapping("/product/editForm/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("productDTO", productService.convertToDTO(id));
        model.addAttribute("id", id);
        return "admin/product_edit";
    }

    @PostMapping("/product/update/{id}")
    public String updateProduct(@Valid @ModelAttribute("productDTO") ProductDTO productDTO, BindingResult result, @PathVariable("id") Long id, Model model) throws IOException {
        if (result.hasErrors()) {
            return "admin/product_edit";
        }
        productService.update(productDTO, id);
        return "redirect:/admin/products";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/product/enabled/{id}")
    public String enabledProduct(@PathVariable("id") Long id, Model model) {
        productService.enabledById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/product/exportToExcel")
    void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=products_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Product> listProducts = productService.findAll();

        ProductExportExcel productExportExcel = new ProductExportExcel(listProducts);

        productExportExcel.export(response);

    }

    @PostMapping("/product/importFile")
    public String readFileUpload(@ModelAttribute("readFileDTO") ReadFileDTO multipartFile, Model model) {
        boolean uploadOke = productImportExcel.saveDataFromUploadFile(multipartFile.getMultipartFile());

        if (uploadOke) {
            model.addAttribute("readFileSuccess", "READ FILE SUCCESS");
        } else {
            model.addAttribute("readFileFailed", "READ FILE FAILED");
        }

        return "redirect:/admin/products";
    }

    @GetMapping("/products/deleteAll")
    public String deleteAll(Model model) {
        productService.deleteAllProduct();
        return "redirect:/admin/products";
    }

    @GetMapping("/products/enableAll")
    public String enableAll(Model model) {
        productService.enabledAllProduct();
        return "redirect:/admin/products";
    }

    @GetMapping("/product/saleAllProduct")
    public String saleAllProduct(Model model) {
        return "admin/products_sale_all";
    }

    @PostMapping("/products/processingSaleAllProduct")
    public String processingSaleAllProduct(@RequestParam("sale") int sale,Model model) {
        productService.saleAllProduct(sale);
        return "redirect:/admin/products";
    }
}
