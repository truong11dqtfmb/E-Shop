package com.dqt.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy="order")
    private List<OrderDetail> orderDetails;

    @Column(name = "order_code")
    private String orderCode;

    private String cus_fullName;

    private String cus_phone;

    private String cus_address;

    private String cus_email;

    private String cus_note;

    private int payments;

    private int status;

    private Date createDate;
}
