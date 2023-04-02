package com.npt.ecommerce_full.dto;

import com.npt.ecommerce_full.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private String costPrice;
    private String salePrice;
    private Category category;
    private int currentQuantity;
    private String image;
    private Boolean activated;
    private Boolean deleted;

}
