package com.dqt.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotEmpty(message = "FullName not Empty!")
    private String fullName;

    @NotEmpty(message = "E-mail not Empty!")
    private String email;

    @NotEmpty(message = "Password not Empty!")
    private String password;

    @NotEmpty(message = "Confirm Password not Empty!")
    private String confirmPassword;


}
