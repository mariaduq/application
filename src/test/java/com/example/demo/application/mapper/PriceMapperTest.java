package com.example.demo.application.mapper;

import com.example.demo.application.output.PriceOutput;
import com.example.demo.domain.model.Price;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceMapperTest {

    private final PriceMapper priceMapper = new PriceMapper();

    @Test
    void should_transform_from_Price_to_PriceOutput() {
        //GIVEN
        Price price = Price.builder()
                .priceList(1)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(2))
                .brandId((long)1)
                .productId(9136275)
                .priority(0)
                .price((float)912.50)
                .curr("EUR")
                .build();

        //WHEN
        PriceOutput priceOutput = priceMapper.toOutput(price);

        //THEN
        assertEquals(price.getPriceList(), priceOutput.getPriceList());
        assertEquals(price.getStartDate(), priceOutput.getStartDate());
        assertEquals(price.getEndDate(), priceOutput.getEndDate());
        assertEquals(price.getBrandId(), priceOutput.getBrandId());
        assertEquals(price.getProductId(), priceOutput.getProductId());
        assertEquals(price.getPriority(), priceOutput.getPriority());
        assertEquals(price.getPrice(), priceOutput.getPrice());
        assertEquals(price.getCurr(), priceOutput.getCurr());
    }
}
