package com.example.demo.application.output;

import com.example.demo.infraestructure.persistence.entities.PriceEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductOutput {

    private long productId;

    private String productType;

    private String brandName;

    private long brandId;

    private String description;

    private int stock;

    private List<PriceEntity> productPrices;
}
