package com.dqt.ecommerce.controller.admin;

import com.dqt.ecommerce.dto.ReadFileDTO;
import com.dqt.ecommerce.model.Product;
import com.dqt.ecommerce.model.User;
import com.dqt.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;


    //    Page & Sort
    @GetMapping("/users")
    public String findAllUser(Model model) {

        return page(model, 1, "id", "asc");
    }

    @GetMapping(value = {"/users/page/{pageNumber}"})
    String page(Model model, @PathVariable(name = "pageNumber") int currentPage,
                @RequestParam("sortField") String sortField,
                @RequestParam("sortDir") String sortDir) {
        Page<User> page = userService.pageFindAll(currentPage, sortField, sortDir);
        long totalItem = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("totalItem", totalItem);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("users", page.getContent());
        return "admin/users";
    }


}
