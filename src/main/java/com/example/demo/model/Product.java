package com.example.demo.model;

import com.example.demo.infraestructure.ddbb.model.PriceEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private long productId;

    private String productType;

    private String brand;

    private long brandId;

    private String description;

    private int stock;

    private List<PriceEntity> productPrices;
}
