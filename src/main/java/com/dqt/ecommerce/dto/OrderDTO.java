package com.dqt.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NotEmpty(message = "FullName Not Empty!")
    private String fullName;

    @NotEmpty(message = "Phone No Not Empty!")
    private String phone;

    @NotEmpty(message = "Address Not Empty!")
    private String address;

    @NotEmpty(message = "E-mail Not Empty!")
    private String email;

    private String note;

    private int payments;

}
