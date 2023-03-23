package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

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
		
		Price price = new Price();
		price = priceService.getCorrectPrice(date);
		
		
		
		
	}

}
