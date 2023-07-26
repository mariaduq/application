package com.example.demo.application.usecases.price;

import com.example.demo.application.mapper.PriceMapper;
import com.example.demo.application.output.PriceOutput;
import com.example.demo.domain.port.PricesPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetProductPricesUseCase {

    private final PricesPort pricesPort;

    private final PriceMapper priceMapper;

    public List<PriceOutput> execute(Long productId) {
        return pricesPort.getProductPrices(productId)
                .stream()
                .map(priceMapper::toOutput)
                .collect(Collectors.toList());
    }

}
