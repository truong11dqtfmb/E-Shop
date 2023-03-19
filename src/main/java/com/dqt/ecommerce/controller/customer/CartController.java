package com.dqt.ecommerce.controller.customer;

import com.dqt.ecommerce.dto.MailThymeleafDTO;
import com.dqt.ecommerce.model.CartItem;
import com.dqt.ecommerce.model.Order;
import com.dqt.ecommerce.service.impl.MailThymeleafService;
import com.dqt.ecommerce.service.impl.ShoppingCartService;
import com.dqt.ecommerce.dto.OrderDTO;
import com.dqt.ecommerce.model.Product;
import com.dqt.ecommerce.service.CategoryService;
import com.dqt.ecommerce.service.OrderService;
import com.dqt.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shop")
public class CartController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private MailThymeleafService mailThymeleafService;




    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("total", cartService.getAmount());
        model.addAttribute("cart", cartService.getAllItems());

        return "customer/cart";
    }

    @GetMapping("/cart/addToCart/{id}")
    public String addToCart(@PathVariable("id") long id, Model model) {
        Product product = productService.findById(id).get();

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setPrice(product.getCost());
        item.setQuantity(1);
        cartService.add(item);

        return "redirect:/shop/cart";
    }

    @PostMapping("/cart/update")
    public String cartUpdate(@RequestParam("id") long id,
                             @RequestParam("qty") int qty) {
        cartService.update(id, qty);

        return "redirect:/shop/cart";
    }


    @GetMapping("/cart/removeItem/{id}")
    public String cartRemoveItem(@PathVariable("id") long id, Model model) {
        cartService.remove(id);
        return "redirect:/shop/cart";
    }

    @GetMapping("/cart/clear")
    public String cartClear(Model model) {
        cartService.clear();
        return "redirect:/shop/cart";
    }

    @GetMapping("/checkout")
    public String checkOut(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/shop/login";
        }
        String email = principal.getName();

        model.addAttribute("cart", cartService.getAllItems());
        model.addAttribute("total", cartService.getAmount());
        model.addAttribute("cartCount", cartService.getCount());
        model.addAttribute("orderDTO", new OrderDTO());
        return "customer/checkout";
    }

    @PostMapping("/processingCheckOut")
    public String processingCheckOut(@Valid @ModelAttribute("orderDTO") OrderDTO orderDTO, BindingResult result, Model model, Principal principal) throws MessagingException, UnsupportedEncodingException {
        if (result.hasErrors()) {
            model.addAttribute("cart", cartService.getAllItems());
            model.addAttribute("total", cartService.getAmount());
            model.addAttribute("cartCount", cartService.getCount());
            return "customer/checkout";
        }


        String email = principal.getName();

        Order order = orderService.save(email, orderDTO);

        long orderIDLast = order.getId();

//        save Order Detail: orderID + cart(cartService.getAllItems())

        for (CartItem item : cartService.getAllItems()) {
            orderService.saveOrderDetail(orderIDLast,item);
        }

        MailThymeleafDTO thymeleafDTO = new MailThymeleafDTO();
        thymeleafDTO.setEmailTo(order.getCus_email());
        thymeleafDTO.setSubject("Xác nhận đơn hàng " + order.getOrderCode() + " đang xử lý");
        thymeleafDTO.setTemplateName("customer/mailThymeleaf");

        Map<String, Object> map = new HashMap<>();
        map.put("myOrder",order);
        map.put("myShoppingCart",cartService.getAllItems());
        map.put("total", cartService.getAmount());

        map.put("name", "DAO QUOC TRUONG ALO 1 3 5 7   AC LO");

        thymeleafDTO.setProps(map);

        boolean check = mailThymeleafService.sendMailWithTemplateThymeleaf(thymeleafDTO);

        if (check) {
            System.out.println("SEND MAIL SUCCESSFULLY!");
        }else{
            System.out.println("SEND MAIL FAILED!");
        }

        System.out.println("CHECK OUT");
        System.out.println(orderDTO);


        System.out.println("DAT HANG THANH CONG");
        model.addAttribute("checkoutSuccess","Đặt hàng thành công!");




//        Send Mail

        cartService.clear();

        model.addAttribute("total", cartService.getAmount());
        model.addAttribute("cart", cartService.getAllItems());
        model.addAttribute("cartCount", cartService.getCount());
        model.addAttribute("categories", categoryService.findAllActived());

        return "customer/messageCheckout";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("categories", categoryService.findAllActived());
        model.addAttribute("cartCount", cartService.getCount());
    }


}
