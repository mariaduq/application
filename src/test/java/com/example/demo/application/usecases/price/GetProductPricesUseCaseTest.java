package com.example.demo.application.usecases.price;

import com.example.demo.application.mapper.PriceMapper;
import com.example.demo.application.output.PriceOutput;
import com.example.demo.infrastructure.persistence.adapter.PriceAdapter;
import com.example.demo.infrastructure.persistence.entities.PriceEntity;
import com.example.demo.infrastructure.persistence.jpa.PriceRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetProductPricesUseCaseTest {

    PriceRepositoryJpa priceRepositoryJpa = Mockito.mock(PriceRepositoryJpa.class);

    PriceAdapter priceAdapter = new PriceAdapter(priceRepositoryJpa);

    GetProductPricesUseCase getProductPricesUseCase = new GetProductPricesUseCase(priceAdapter, new PriceMapper());

    @Test
    void product_prices_when_productId_is_9136275() {
        //GIVEN
        when(priceRepositoryJpa.findAll())
                .thenReturn(List.of(
                        buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 9136275),
                        buildPrice((float)1245.50, Timestamp.valueOf("2024-01-07 00:00:00"), Timestamp.valueOf("2024-03-01 23:59:59"), 1, 9136275),
                        buildPrice((float)830.50, Timestamp.valueOf("2024-07-01 00:00:00"), Timestamp.valueOf("2024-09-28 23:59:59"), 1, 9136275),
                        buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 6216547),
                        buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 9459432),
                        buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 3837464)));

        long productId = 9136275;

        //WHEN
        List<PriceOutput> productPrices = getProductPricesUseCase.execute(productId);

        //THEN
        assertEquals(3, productPrices.size());

        assertEquals((float)912.50, productPrices.get(0).getPrice());
        assertEquals((float)1245.50, productPrices.get(1).getPrice());
        assertEquals((float)830.50, productPrices.get(2).getPrice());
    }

    @Test
    void product_prices_when_productId_is_9459432() {
        //GIVEN
        when(priceRepositoryJpa.findAll())
                .thenReturn(List.of(
                        buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 9136275),
                        buildPrice((float)1245.50, Timestamp.valueOf("2024-01-07 00:00:00"), Timestamp.valueOf("2024-03-01 23:59:59"), 1, 9136275),
                        buildPrice((float)830.50, Timestamp.valueOf("2024-07-01 00:00:00"), Timestamp.valueOf("2024-09-28 23:59:59"), 1, 9136275),
                        buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 6216547),
                        buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 9459432),
                        buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 3837464)));

        long productId = 9459432;

        //WHEN
        List<PriceOutput> productPrices = getProductPricesUseCase.execute(productId);

        //THEN
        assertEquals(1, productPrices.size());

        assertEquals((float)912.50, productPrices.get(0).getPrice());
    }

    @Test
    void product_prices_when_productId_not_exists() {
        //GIVEN
        when(priceRepositoryJpa.findAll())
                .thenReturn(List.of(
                        buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 9136275),
                        buildPrice((float)1245.50, Timestamp.valueOf("2024-01-07 00:00:00"), Timestamp.valueOf("2024-03-01 23:59:59"), 1, 9136275),
                        buildPrice((float)830.50, Timestamp.valueOf("2024-07-01 00:00:00"), Timestamp.valueOf("2024-09-28 23:59:59"), 1, 9136275),
                        buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 6216547),
                        buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 9459432),
                        buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 3837464)));

        long productId = 94594323;

        //WHEN
        List<PriceOutput> productPrices = getProductPricesUseCase.execute(productId);

        //THEN
        assertThat(productPrices.isEmpty());
    }

    private PriceEntity buildPrice(float amount, Timestamp startDate, Timestamp endDate, int priority, long productId) {
        PriceEntity price = new PriceEntity();

        price.setBrandId(1);
        price.setCurr("EUR");
        price.setStartDate(startDate);
        price.setEndDate(endDate);
        price.setPrice(amount);
        price.setProductId(productId);
        price.setPriority(priority);

        return price;
    }
}
