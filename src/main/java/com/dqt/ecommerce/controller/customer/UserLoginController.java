package com.dqt.ecommerce.controller.customer;

import com.dqt.ecommerce.dto.UserDTO;
import com.dqt.ecommerce.model.User;
import com.dqt.ecommerce.service.UserService;
import com.dqt.ecommerce.util.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RequestMapping("/shop")
@Controller
public class UserLoginController {
    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String login() {
        return "customer/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDTO", new UserDTO());

        return "customer/register";
    }


    @PostMapping("/processingRegistration")
    public String processingRegistration(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
                                         BindingResult bindingResult, Model model,
                                         HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        if (bindingResult.hasErrors()) {
            return "customer/register";
        }
        if (userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            User user = userService.registerUser(userDTO);

            String verifyURL = Utility.getSiteURL(request) + "/shop/verify?code=" + user.getVerificationCode();

            userService.sendVerificationEmail(user, verifyURL);

            model.addAttribute("success", "Create Account Successfully!");
            return "admin/verify";
        } else {
            model.addAttribute("passwordNotValid", "Password and Confirm password not valid!");
        }

        return "customer/register";
    }

    //    Verify email
    @GetMapping("/verify")
    public String verifyCustomer(@RequestParam("code") String code, Model model) {
        boolean verified = userService.verify(code);

        String statusVerified = verified ? "Verification Succeeded!" : "Verification Failed!";
        model.addAttribute("statusVerified", statusVerified);

        return "customer/verify";
    }


    //    Forgot Password
    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "customer/password";
    }

    @PostMapping("/processingForgotPassword")
    public String processingForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/shop/reset_password?token=" + token;
            userService.sendForgotPassword(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
        return "customer/password";
    }


    //    reset password
    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "customer/message";
        }

        return "customer/resetPassword";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");

        if (password.equals(newPassword)) {
            User user = userService.getByResetPasswordToken(token);
            model.addAttribute("title", "Reset your password");

            if (user == null) {
                model.addAttribute("message", "Invalid Token");
                return "customer/message";
            } else {
                userService.updatePassword(user, password);

                model.addAttribute("message", "You have successfully changed your password.");
            }
            return "customer/message";
        } else {
            model.addAttribute("errorPassword", "Password & Confirm Password not valid!");
            return "customer/resetPassword";

        }
    }
}
