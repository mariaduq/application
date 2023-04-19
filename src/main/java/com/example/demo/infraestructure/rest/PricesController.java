package com.example.demo.infraestructure.rest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.GetAllPricesUseCase;
import com.example.demo.application.GetPricesUseCase;
import com.example.demo.infraestructure.ddbb.PriceRepositoryJpa;
import com.example.demo.infraestructure.ddbb.model.PriceEntity;
import com.example.demo.model.Price;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/practicaPrices")
public class PricesController {
	
	@Autowired
	PriceRepositoryJpa priceRepository;
	
	@Autowired
	GetPricesUseCase getPricesUseCase;
	
	@Autowired
	GetAllPricesUseCase getAllPricesUseCase;
	
	@PostConstruct
	public void init() {
		priceRepository.save(new PriceEntity(1, Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 1, 35455, 0, 35.50f, "EUR"));
		priceRepository.save(new PriceEntity(2, Timestamp.valueOf("2020-06-14 15:00:00"), Timestamp.valueOf("2020-06-14 18:30:00"), 1, 35455, 1, 25.450f, "EUR"));
		priceRepository.save(new PriceEntity(3, Timestamp.valueOf("2020-06-15 00:00:00"), Timestamp.valueOf("2020-06-15 11:00:00"), 1, 35455, 1, 30.50f, "EUR"));
		priceRepository.save(new PriceEntity(4, Timestamp.valueOf("2020-06-15 16:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 1, 35455, 1, 38.950f, "EUR"));
	}
	
	@GetMapping("/v1/prices")
	public ResponseEntity<?> getPrice(@RequestParam (required=false) String dateString,
			@RequestParam (required=false) Long productId, @RequestParam (required=false) Long brandId){
		
		ModelMapper mapper = new ModelMapper();
		
		if (dateString != null && productId != null && brandId != null) {
						
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
			
			Price price = getPricesUseCase.getCorrectPrice(dateTime, productId, brandId);
			PriceDTO priceDTO = mapper.map(price, PriceDTO.class);
			
			return ResponseEntity.ok()
					.body(priceDTO);
		}
		
		else if (dateString == null && productId == null && brandId == null) {
			
			return ResponseEntity.ok()
					.body(getAllPricesUseCase
						.findAll()
						.stream()
						.map((price) -> mapper.map(price, PriceDTO.class))
						.collect(Collectors.toList()));
		}
		
		else {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("HTTP ERROR 400");
		}
	}
	
}
