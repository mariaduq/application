package com.example.demo.application.usecases.price;

import com.example.demo.domain.port.PricesPort;
import com.example.demo.domain.Price;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetProductPricesUseCase {

    private final PricesPort pricesPort;

    public List<Price> execute(Long productId) {
        return pricesPort.getProductPrices(productId);
    }

}
