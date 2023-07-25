package com.example.demo.application.usecases.price;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.application.mapper.PriceMapper;
import com.example.demo.application.output.PriceOutput;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.infrastructure.persistence.adapter.PriceAdapter;
import com.example.demo.infrastructure.persistence.jpa.PriceRepositoryJpa;
import com.example.demo.infrastructure.persistence.entities.PriceEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetPricesUseCaseTest {
	
	PriceRepositoryJpa priceRepositoryJpa = Mockito.mock(PriceRepositoryJpa.class);
	
	PriceAdapter priceAdapter = new PriceAdapter(priceRepositoryJpa);
	
	GetPricesUseCase getPricesUseCase = new GetPricesUseCase(priceAdapter, new PriceMapper());
	
	
	@Test
	void price_when_date_is_2024_06_14_10_00_and_productId_is_9136275() throws Exception {
		//GIVEN
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
				buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 9136275)));
		
		LocalDateTime date = LocalDateTime.of(2024, 06, 14, 10, 00, 00);
		long productId = 9136275;

		//WHEN
		PriceOutput price = getPricesUseCase.execute(date, productId);

		//THEN
		assertEquals((float)912.50, price.getPrice());
	}
	
	
	@Test
	void price_when_date_is_2024_01_8_16_00_and_productId_is_9136275() throws Exception {
		//GIVEN
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
				buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 9136275),
				buildPrice((float)850.50, Timestamp.valueOf("2024-01-07 00:00:00"), Timestamp.valueOf("2024-03-01 23:59:59"), 1, 9136275)));
		
		LocalDateTime date = LocalDateTime.of(2024, 01, 8, 16, 00, 00);
		long productId = 9136275;

		//WHEN
		PriceOutput price = getPricesUseCase.execute(date, productId);

		//THEN
		assertEquals((float)850.50, price.getPrice());
	}
	
	
	@Test
	void price_when_date_is_2024_01_01_10_00_and_productId_is_9136275() throws Exception {
		//GIVEN
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
				buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 9136275)));
		
		LocalDateTime date = LocalDateTime.of(2024, 01, 01, 10, 00, 00);
		long productId = 9136275;

		//WHEN
		PriceOutput price = getPricesUseCase.execute(date, productId);

		//THEN
		assertEquals((float)912.50, price.getPrice());
	}
	
	
	@Test
	void price_when_date_is_2024_09_28_23_58_and_productId_is_9136275() throws Exception {
		//GIVEN
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
				buildPrice((float)912.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 9136275),
				buildPrice((float)840.50, Timestamp.valueOf("2024-07-01 00:00:00"), Timestamp.valueOf("2024-09-28 23:59:59"), 1, 9136275)));
				
		LocalDateTime date = LocalDateTime.of(2024, 9, 28, 23, 58, 00);
		long productId = 9136275;

		//WHEN
		PriceOutput price = getPricesUseCase.execute(date, productId);

		//THEN
		assertEquals((float)840.50, price.getPrice());
	}
	
	@Test
	void price_when_date_is_2024_09_28_23_58_and_productId_is_6216547() throws Exception {
		//GIVEN
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
				buildPrice((float)1245.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 6216547),
				buildPrice((float)1005.50, Timestamp.valueOf("2024-01-07 00:00:00"), Timestamp.valueOf("2024-03-01 23:59:59"), 1, 6216547),
				buildPrice((float)1000.50, Timestamp.valueOf("2024-07-01 00:00:00"), Timestamp.valueOf("2024-09-28 23:59:59"), 1, 6216547)));
				
		LocalDateTime date = LocalDateTime.of(2024, 9, 28, 23, 58, 00);
		long productId = 6216547;

		//WHEN
		PriceOutput price = getPricesUseCase.execute(date, productId);

		//THEN
		assertEquals((float)1000.50, price.getPrice());
	}
	
	
	@Test
	void priceForInvalidProduct() {
		//GIVEN
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
				buildPrice((float)1245.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 6216547),
				buildPrice((float)1005.50, Timestamp.valueOf("2024-01-07 00:00:00"), Timestamp.valueOf("2024-03-01 23:59:59"), 1, 6216547)));

		LocalDateTime date = LocalDateTime.of(2024, 06, 16, 21, 00, 00);
		long productId = 621654;

		//WHEN; THEN
		assertThrows(Exception.class, ()->getPricesUseCase.execute(date, productId));
	}
	
	
	@Test
	void priceForInvalidDate() {
		//GIVEN
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
						buildPrice((float)1245.50, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-12-31 23:59:59"), 0, 6216547),
						buildPrice((float)1005.50, Timestamp.valueOf("2024-01-07 00:00:00"), Timestamp.valueOf("2024-03-01 23:59:59"), 1, 6216547)));

		LocalDateTime date = LocalDateTime.of(2025, 06, 16, 21, 00, 00);
		long productId = 6216547;

		//WHEN; THEN
		assertThrows(Exception.class, ()->getPricesUseCase.execute(date, productId));
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
