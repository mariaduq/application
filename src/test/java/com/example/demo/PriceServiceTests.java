package com.example.demo;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.application.GetPricesUseCase;
import com.example.demo.model.Price;

import jakarta.persistence.EntityNotFoundException;

import com.example.demo.infraestructure.ddbb.PriceAdapter;
import com.example.demo.infraestructure.ddbb.PriceRepositoryJpa;
import com.example.demo.infraestructure.ddbb.model.PriceEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceServiceTests {
	
	PriceRepositoryJpa priceRepositoryJpa = Mockito.mock(PriceRepositoryJpa.class);
	
	PriceAdapter priceAdapter = new PriceAdapter(priceRepositoryJpa);
	
	GetPricesUseCase getPricesUseCase = new GetPricesUseCase(priceAdapter);
	
	
	@Test
	void price_when_date_is_2020_06_14_10_00_and_productId_is_35455_and_brandId_is_1() {		
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
				buildPrice((float)35.5, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 0)));
		
		Timestamp date = Timestamp.valueOf("2020-06-14 10:00:00");
		long product_id = 35455;
		long brand_id = 1;

		Price price = getPricesUseCase.getCorrectPrice(date, product_id, brand_id);
		
		assertEquals((float)35.5, price.getPrice());
	}
	
	@Test
	void price_when_date_is_2020_06_14_16_00_and_productId_is_35455_and_brandId_is_1() {
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
				buildPrice((float)35.5, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 0),
				buildPrice((float)25.45, Timestamp.valueOf("2020-06-14 15:00:00"), Timestamp.valueOf("2020-06-14 18:30:00"), 1)));
		
		Timestamp date = Timestamp.valueOf("2020-06-14 16:00:00");
		long product_id = 35455;
		long brand_id = 1;
		
		Price price = getPricesUseCase.getCorrectPrice(date, product_id, brand_id);
		
		assertEquals((float)25.45, price.getPrice());
	}
	
	
	@Test
	void price_when_date_is_2020_06_14_21_00_and_productId_is_35455_and_brandId_is_1() {
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
				buildPrice((float)35.5, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 0)));
		
		Timestamp date = Timestamp.valueOf("2020-06-14 21:00:00");
		long product_id = 35455;
		long brand_id = 1;
		
		Price price = getPricesUseCase.getCorrectPrice(date, product_id, brand_id);
		
		assertEquals((float)35.5, price.getPrice());
	}
	
	
	@Test
	void price_when_date_is_2020_06_15_10_00_and_productId_is_35455_and_brandId_is_1() {
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
				buildPrice((float)35.5, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 0),
				buildPrice((float)30.5, Timestamp.valueOf("2020-06-15 00:00:00"), Timestamp.valueOf("2020-06-15 11:00:00"), 1)));
		
		Timestamp date = Timestamp.valueOf("2020-06-15 10:00:00");
		long product_id = 35455;
		long brand_id = 1;
		
		Price price = getPricesUseCase.getCorrectPrice(date, product_id, brand_id);
		
		assertEquals((float)30.5, price.getPrice());
	}
	
	@Test
	void price_when_date_is_2020_06_16_21_00_and_productId_is_35455_and_brandId_is_1() {
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
				buildPrice((float)35.5, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 0),
				buildPrice((float)38.95, Timestamp.valueOf("2020-06-15 16:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 1)));
		
		Timestamp date = Timestamp.valueOf("2020-06-16 21:00:00");
		long product_id = 35455;
		long brand_id = 1;
		
		Price price = getPricesUseCase.getCorrectPrice(date, product_id, brand_id);
		
		assertEquals((float)38.95, price.getPrice());
	}
	
	
	@Test
	void priceForInvalidProduct() {
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
				buildPrice((float)35.5, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 0),
				buildPrice((float)38.95, Timestamp.valueOf("2020-06-15 16:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 1)));
		
		Timestamp date = Timestamp.valueOf("2020-06-16 21:00:00");
		long product_id = 35457;
		long brand_id = 1;
				
		assertThrows(EntityNotFoundException.class, ()->getPricesUseCase.getCorrectPrice(date, product_id, brand_id));
	}
	
	
	@Test
	void priceForInvalidDate() {
		when(priceRepositoryJpa.findAll())
				.thenReturn(List.of(
				buildPrice((float)35.5, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 0),
				buildPrice((float)38.95, Timestamp.valueOf("2020-06-15 16:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 1)));

		Timestamp date = Timestamp.valueOf("2021-06-16 21:00:00");
		long product_id = 35455;
		long brand_id = 1;
				
		assertThrows(EntityNotFoundException.class, ()->getPricesUseCase.getCorrectPrice(date, product_id, brand_id));
	}

	private PriceEntity buildPrice(float amount, Timestamp startDate, Timestamp endDate, int priority) {
		PriceEntity price = new PriceEntity();
		
		price.setBrand_id(1);
		price.setCurr("EUR");
		price.setStart_date(startDate);
		price.setEnd_date(endDate);
		price.setPrice(amount);
		price.setProduct_id(35455);
		price.setPriority(priority);
		
		return price;
	}
	
	
	
}
