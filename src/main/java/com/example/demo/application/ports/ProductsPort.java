package com.example.demo.application.ports;

import com.example.demo.model.Product;

import java.util.List;

public interface ProductsPort {

    public List<Product> getAllProducts();
}
