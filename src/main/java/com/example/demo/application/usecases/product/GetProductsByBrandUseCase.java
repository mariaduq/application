package com.example.demo.application.usecases.product;

import com.example.demo.application.mapper.ProductMapper;
import com.example.demo.application.output.ProductOutput;
import com.example.demo.domain.port.ProductsPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetProductsByBrandUseCase {

    private final ProductsPort productsPort;

    private final ProductMapper productMapper;

    public List<ProductOutput> execute(String brandName) throws Exception {
        List<ProductOutput> list = productsPort.getAllProducts()
                .stream()
                .filter(product -> product.getBrandName().equalsIgnoreCase(brandName))
                .map(productMapper::toOutput)
                .toList();

        if (list.isEmpty()) throw new Exception("No tenemos ningún producto de esa marca.");
        return list;
    }
}
