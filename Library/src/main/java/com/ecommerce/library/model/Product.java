package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double costPrice;
    private double salePrice;
    private int currentQuantity;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;
    private boolean is_deleted;
    private boolean is_activated;
}
