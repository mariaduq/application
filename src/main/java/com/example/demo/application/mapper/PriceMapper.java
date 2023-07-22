package com.example.demo.application.mapper;

import com.example.demo.application.output.PriceOutput;
import com.example.demo.application.output.ProductOutput;
import com.example.demo.domain.model.Price;
import com.example.demo.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Component("applicationPriceMapper")
@Service
public class PriceMapper {

    public PriceOutput toOutput(Price price) {
        return PriceOutput.builder()
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .brandId(price.getBrandId())
                .productId(price.getProductId())
                .priority(price.getPriority())
                .price(price.getPrice())
                .curr(price.getCurr())
                .build();
    }
}
