package com.example.demo.infraestructure.persistence.adapter;

import com.example.demo.domain.port.ProductsPort;
import com.example.demo.infraestructure.persistence.jpa.ProductRepositoryJpa;
import com.example.demo.infraestructure.persistence.mappers.ProductMapper;
import com.example.demo.domain.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductAdapter implements ProductsPort {

    private final ProductRepositoryJpa productRepositoryJpa;

    private final ProductMapper productMapper;

    @Override
    public List<Product> getAllProducts() {
        return productRepositoryJpa.findAll()
                .stream()
                .map(productMapper::toDomain)
                .collect(Collectors.toList());
    }
}
