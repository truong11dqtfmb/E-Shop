package com.dqt.ecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotEmpty(message = "Product Name not empty!")
    private String name;

    @NotNull(message = "Category not null!")
    private Long categoryId;

    @NotEmpty(message = "Product Title not empty!")
    private String title;

    private String description;

    @NotNull(message = "Product Price not null!")
    private Long price;

    private int sale;

    private MultipartFile image;

    private boolean isActived;
    private boolean isDeleted;
}
