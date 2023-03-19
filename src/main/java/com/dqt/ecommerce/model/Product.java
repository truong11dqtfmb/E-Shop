package com.dqt.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @OneToMany(mappedBy="product")
    private List<OrderDetail> orderDetails;

    private String title;

    @Lob
    private String description;

    private Long price;

    private int sale;

    private Long cost;

    private String image;

    @Column(name = "is_actived")
    private boolean isActived;

    @Column(name = "is_deleted")
    private boolean isDeleted;


}
