package com.example.demo.infraestructure.rest.dto;

import com.example.demo.infraestructure.persistence.entities.PriceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private long productId;

    private String productType;

    private String brandName;

    private long brandId;

    private String description;

    private int stock;

    private List<PriceEntity> productPrices;
}
