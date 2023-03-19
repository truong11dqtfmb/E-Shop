package com.dqt.ecommerce.controller.admin;


import com.dqt.ecommerce.model.Order;
import com.dqt.ecommerce.model.OrderDetail;
import com.dqt.ecommerce.service.OrderDetailService;
import com.dqt.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;



    //    Page & Sort
    @GetMapping("/orders")
    public String findAllOrder(Model model) {

        return page(model, 1, "id", "asc");
    }

    @GetMapping(value = {"/orders/page/{pageNumber}"})
    String page(Model model, @PathVariable(name = "pageNumber") int currentPage,
                @RequestParam("sortField") String sortField,
                @RequestParam("sortDir") String sortDir) {
        Page<Order> page = orderService.pageFindAll(currentPage, sortField, sortDir);
        long totalItem = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("totalItem", totalItem);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("orders", page.getContent());
        return "admin/orders";
    }

    @GetMapping("/order/{id}")
    public String orderDetail(@PathVariable("id") long id, Model model){
        Order order = orderService.findById(id);

        List<OrderDetail> list = orderDetailService.findAllByOrderId(id);

        model.addAttribute("order",order);
        model.addAttribute("orderDetailList",list);

        return "admin/orderDetail";
    }


}
