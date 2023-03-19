package com.dqt.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", length = 45)
    @NotEmpty(message = "Category Name not empty!")
    private String name;

    @OneToMany(mappedBy="category")
    private List<Product> products;

    @Column(name = "is_actived")
    private boolean isActived;

    @Column(name = "is_deleted")
    private boolean isDeleted;


}
