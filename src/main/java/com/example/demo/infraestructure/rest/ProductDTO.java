package com.example.demo.infraestructure.rest;

import com.example.demo.infraestructure.ddbb.model.PriceEntity;
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

    private String brand;

    private long brandId;

    private String description;

    private int stock;

    private List<PriceEntity> productPrices;
}
