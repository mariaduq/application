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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetAllPricesUseCaseTest {

    PriceRepositoryJpa priceRepositoryJpa = Mockito.mock(PriceRepositoryJpa.class);

    PriceAdapter priceAdapter = new PriceAdapter(priceRepositoryJpa);

    GetAllPricesUseCase getAllPricesUseCase = new GetAllPricesUseCase(priceAdapter, new PriceMapper());

    @Test
    void should_return_all_prices() {
        //GIVEN
        when(priceRepositoryJpa.findAll())
                .thenReturn(List.of(
                        buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 9136275),
                        buildPrice((float)1245.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 6216547),
                        buildPrice((float)830.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 9459432),
                        buildPrice((float)700.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 3837464),
                        buildPrice((float)840.50, Timestamp.valueOf("2024-07-01 00:00:00"), Timestamp.valueOf("2024-09-28 23:59:59"), 1, 9136275)));

        //WHEN
        List<PriceOutput> allPrices = getAllPricesUseCase.execute();

        //THEN
        assertEquals(5, allPrices.size());

        assertEquals(allPrices.get(0).getPrice(), (float)912.50);
        assertEquals(allPrices.get(1).getPrice(), (float)1245.50);
        assertEquals(allPrices.get(2).getPrice(), (float)830.50);
        assertEquals(allPrices.get(3).getPrice(), (float)700.50);
        assertEquals(allPrices.get(4).getPrice(), (float)840.50);

        assertEquals(allPrices.get(0).getProductId(), 9136275);
        assertEquals(allPrices.get(1).getProductId(), 6216547);
        assertEquals(allPrices.get(2).getProductId(), 9459432);
        assertEquals(allPrices.get(3).getProductId(), 3837464);
        assertEquals(allPrices.get(4).getProductId(), 9136275);

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
