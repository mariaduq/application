package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.application.PriceService;
import com.example.demo.infraestructure.PriceRepository;
import com.example.demo.ui.PriceDTO;


@SpringBootTest
class PracticaPricesApplicationTests {
	
	@Mock
	PriceRepository priceRepository;
	
	@Mock
	PriceService priceService;
	
	@BeforeEach
	public void setUp() {
		priceService = new PriceService(priceRepository);
	}
	
	@Test
	void priceForDay14Hour10() {
		Timestamp date = Timestamp.valueOf("2020-06-14 10:00:00");
		long product_id = 35455;
				
		PriceDTO price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(35.50);		
	}
	
	@Test
	void priceForDay14Hour16() {
		Timestamp date = Timestamp.valueOf("2020-06-14 16:00:00");
		long product_id = 35455;
		
		PriceDTO price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(25.45);
	}
	
	@Test
	void priceForDay14Hour21() {
		Timestamp date = Timestamp.valueOf("2020-06-14 21:00:00");
		long product_id = 35455;
				
		PriceDTO price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(35.50);
	}
	
	@Test
	void priceForDay15Hour10() {
		Timestamp date = Timestamp.valueOf("2020-06-15 10:00:00");
		long product_id = 35455;
				
		PriceDTO price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(30.50);
	}
	
	
	@Test
	void priceForDay16Hour21() {
		Timestamp date = Timestamp.valueOf("2020-06-16 21:00:00");
		long product_id = 35455;
				
		PriceDTO price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(38.95);
	}
	
	@Test
	void priceForInvalidProduct() {
		Timestamp date = Timestamp.valueOf("2020-07-16 21:00:00");
		long product_id = 35458;
				
		PriceDTO price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.equals(null));
	}
	
	@Test
	void priceForInvalidDate() {
		Timestamp date = Timestamp.valueOf("2021-07-16 21:00:00");
		long product_id = 35455;
				
		PriceDTO price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.equals(null));
	}
	
}
