package com.dqt.ecommerce.service;

import com.dqt.ecommerce.dto.UserDTO;
import com.dqt.ecommerce.model.Product;
import com.dqt.ecommerce.model.User;
import org.springframework.data.domain.Page;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    User registerAdmin(UserDTO userDTO);

    User registerUser(UserDTO userDTO);

    void sendVerificationEmail(User user, String verifyURL) throws MessagingException, UnsupportedEncodingException;

    boolean verify(String code);

    void updateResetPasswordToken(String token, String email);

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);

    void sendForgotPassword(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException;

    User findByEmail(String email);

    List<User> findAll();

    User findById(long id);

    User enabledAccount(long id);

    Page<User> pageFindAll(int pageNumber, String sortField, String sortDir);

    long countUser();

}
