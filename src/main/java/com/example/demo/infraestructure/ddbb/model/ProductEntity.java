package com.example.demo.infraestructure.ddbb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public @Data class ProductEntity {

    @Id
    @Column(name = "PRODUCT_ID")
    private long productId;

    @Column(name = "PRODUCT_TYPE")
    private String productType;

    @Column(name = "BRAND_NAME")
    private String brandName;

    @Column(name = "BRAND_ID")
    private long brandId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STOCK")
    private int stock;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private List<PriceEntity> productPrices = new ArrayList<>();
}
