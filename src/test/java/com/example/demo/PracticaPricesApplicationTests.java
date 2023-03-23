package com.example.demo;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.infraestructure.PriceRepository;

@SpringBootTest
class PracticaPricesApplicationTests {
	
	@Mock
	PriceRepository priceRepository;
	
	@Test
	void priceForDay14Hour10() {
		Timestamp date = Timestamp.valueOf("2020-06-14 10:00:00");
		long product_id = 35455;
	}

}
