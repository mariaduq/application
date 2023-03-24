package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.infraestructure.PriceRepository;
import com.example.demo.infraestructure.model.Price;

@SpringBootTest
class PracticaPricesApplicationTests {
	
	@Mock
	PriceRepository priceRepository;
	
	@Mock
	PriceService priceService;
	
	@Test
	void priceForDay14Hour10() {
		Timestamp date = Timestamp.valueOf("2020-06-14 10:00:00");
		long product_id = 35455;
		
		priceService = new PriceService(priceRepository);
		
		Price price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(35.50);		
	}
	
	@Test
	void priceForDay14Hour16() {
		Timestamp date = Timestamp.valueOf("2020-06-14 16:00:00");
		long product_id = 35455;
		
		priceService = new PriceService(priceRepository);
		
		Price price = new Price();
		price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(25.45);
	}
	
	@Test
	void priceForDay14Hour21() {
		Timestamp date = Timestamp.valueOf("2020-06-14 21:00:00");
		long product_id = 35455;
		
		priceService = new PriceService(priceRepository);
		
		Price price = new Price();
		price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(35.50);
	}
	
	@Test
	void priceForDay15Hour10() {
		Timestamp date = Timestamp.valueOf("2020-06-15 10:00:00");
		long product_id = 35455;
		
		priceService = new PriceService(priceRepository);
		
		Price price = new Price();
		price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(30.50);
	}
	
	
	@Test
	void priceForDay16Hour21() {
		Timestamp date = Timestamp.valueOf("2020-06-16 21:00:00");
		long product_id = 35455;
		
		priceService = new PriceService(priceRepository);
		
		Price price = new Price();
		price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.getPrice()).isEqualTo(38.95);
	}
	
	@Test
	void priceForInvalidProduct() {
		Timestamp date = Timestamp.valueOf("2020-07-16 21:00:00");
		long product_id = 35458;
		
		priceService = new PriceService(priceRepository);
		
		Price price = new Price();
		price = priceService.getCorrectPrice(date, product_id);
		
		assertThat(price.equals(null));
	}

}
