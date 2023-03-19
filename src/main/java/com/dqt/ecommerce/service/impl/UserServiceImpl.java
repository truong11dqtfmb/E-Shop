package com.dqt.ecommerce.service.impl;

import com.dqt.ecommerce.constant.SystemConstant;
import com.dqt.ecommerce.dto.UserDTO;
import com.dqt.ecommerce.model.Product;
import com.dqt.ecommerce.model.Role;
import com.dqt.ecommerce.model.User;
import com.dqt.ecommerce.repository.UserRepository;
import com.dqt.ecommerce.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

//  Register & Verify Email
    @Override
    public User registerAdmin(UserDTO userDTO) {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setEnabled(false);
        user.setRole(Role.ADMIN);
        user.setVerificationCode(RandomString.make(64));
        return userRepository.save(user);
    }

    @Override
    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setEnabled(false);
        user.setRole(Role.USER);
        user.setVerificationCode(RandomString.make(64));
        return userRepository.save(user);
    }



    @Override
    public void sendVerificationEmail(User user, String verifyURL) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String toAddress = user.getEmail();
        String subject = "Please verify your registration";
        String content = "Dear " + user.getFullName() + ",<br>"
                + "Please click the link below to verify your registration:<br>";

        content += "<h3><a href=\"" + verifyURL + "\">VERIFY EMAIL</a></h3>";

        content += "Thank you,<br>" + "Your company name.";

        helper.setFrom("quoctruong11tv@gmail.com", "ĐÀO QUỐC TRƯỢNG!");
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    public boolean verify(String code) {
        User user = userRepository.findByVerificationCode(code);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
    }


//    Reset Password

    @Override
    public void updateResetPasswordToken(String token, String email) {

        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        }
    }
    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public void sendForgotPassword(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + resetPasswordLink + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setFrom("quoctruong11tv@gmail.com", "ĐÀO QUỐC TRƯỢNG!");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User enabledAccount(long id) {
        User user = userRepository.findById(id).get();

        user.setEnabled(false);

        return userRepository.save(user);
    }

    @Override
    public Page<User> pageFindAll(int pageNumber, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, SystemConstant.PAGE_SIZE, sort);

        return userRepository.findAll(pageable);
    }

    @Override
    public long countUser() {
        return userRepository.count();
    }

}
