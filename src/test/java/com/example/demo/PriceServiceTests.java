package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.application.PriceService;
import com.example.demo.infraestructure.PriceAggregateDAO;
import com.example.demo.infraestructure.PriceRepository;
import com.example.demo.infraestructure.model.Price;
import com.example.demo.ui.PriceDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class PriceServiceTests {
	
	@Mock
	PriceRepository priceRepository;
	
	PriceAggregateDAO priceAggregateDAO;
	
	PriceService priceService;
	
	@BeforeEach
	public void setUp() {
		priceAggregateDAO = new PriceAggregateDAO(priceRepository);
		
		priceService = new PriceService(priceAggregateDAO);
	}
	
	@Test
	void priceForDay14Hour10() {		
		when(priceRepository.findAll())
				.thenReturn(List.of(
				buildPrice(35.5f, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"))));
		
		Timestamp date = Timestamp.valueOf("2020-06-14 10:00:00");
		long product_id = 35455;

		float price = priceService.getCorrectPrice(date, product_id);
		
		assertEquals(35.5, price);
	}
	/*
	@Test
	void priceForDay14Hour16() {
		Timestamp date = Timestamp.valueOf("2020-06-14 16:00:00");
		long product_id = 35455;
		
		when(priceRepository.findAll())
				.thenReturn(List.of(
				buildPrice(35.5f, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59")),
				buildPrice(35.5f, Timestamp.valueOf("2020-06-14 10:00:00"), Timestamp.valueOf("2020-06-14 10:00:00"))));
		
		PriceDTO price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(25.45);
	}
	
	@Test
	void priceForDay14Hour21() {
		Timestamp date = Timestamp.valueOf("2020-06-14 21:00:00");
		long product_id = 35455;
		
		when(priceRepository.findAll())
				.thenReturn(List.of(
				buildPrice(35.5f, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59")),
				buildPrice(35.5f, Timestamp.valueOf("2020-06-14 10:00:00"), Timestamp.valueOf("2020-06-14 10:00:00"))));
				
		PriceDTO price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(35.50);
	}
	
	@Test
	void priceForDay15Hour10() {
		Timestamp date = Timestamp.valueOf("2020-06-15 10:00:00");
		long product_id = 35455;
		
		when(priceRepository.findAll())
				.thenReturn(List.of(
				buildPrice(35.5f, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59")),
				buildPrice(35.5f, Timestamp.valueOf("2020-06-14 10:00:00"), Timestamp.valueOf("2020-06-14 10:00:00"))));
				
		PriceDTO price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(30.50);
	}
	
	
	@Test
	void priceForDay16Hour21() {
		Timestamp date = Timestamp.valueOf("2020-06-16 21:00:00");
		long product_id = 35455;
		
		when(priceRepository.findAll())
				.thenReturn(List.of(
				buildPrice(35.5f, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59")),
				buildPrice(35.5f, Timestamp.valueOf("2020-06-14 10:00:00"), Timestamp.valueOf("2020-06-14 10:00:00"))));
				
		PriceDTO price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(38.95);
	}
	
	@Test
	void priceForInvalidProduct() {
		Timestamp date = Timestamp.valueOf("2020-07-16 21:00:00");
		long product_id = 35458;
		
		when(priceRepository.findAll())
				.thenReturn(List.of(
				buildPrice(35.5f, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59")),
				buildPrice(35.5f, Timestamp.valueOf("2020-06-14 10:00:00"), Timestamp.valueOf("2020-06-14 10:00:00"))));
				
		PriceDTO price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.equals(null));
	}
	
	@Test
	void priceForInvalidDate() {
		Timestamp date = Timestamp.valueOf("2021-07-16 21:00:00");
		long product_id = 35455;
		
		when(priceRepository.findAll())
				.thenReturn(List.of(
				buildPrice(35.5f, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59")),
				buildPrice(35.5f, Timestamp.valueOf("2020-06-14 10:00:00"), Timestamp.valueOf("2020-06-14 10:00:00"))));
				
		PriceDTO price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.equals(null));
	}*/

	private Price buildPrice(float amount, Timestamp startDate, Timestamp endDate) {
		Price price = new Price();
		
		price.setBrand_id(1);
		price.setCurr("EUR");
		price.setStart_date(startDate);
		price.setEnd_date(endDate);
		price.setPrice(amount);
		price.setProduct_id(35455);
		
		return price;
	}
	
	
	
}
