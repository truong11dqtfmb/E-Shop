package com.dqt.ecommerce.dto;

import lombok.*;

import java.util.Map;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MailThymeleafDTO {
    private String emailTo;
    private String subject;
    private String templateName;
    private Map<String, Object> props;
}