package com.example.demo.infraestructure.rest;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.GetAllPricesUseCase;
import com.example.demo.application.GetPricesUseCase;
import com.example.demo.infraestructure.ddbb.PriceRepositoryJpa;
import com.example.demo.infraestructure.ddbb.model.PriceEntity;
import com.example.demo.model.Price;

import jakarta.annotation.PostConstruct;

@RestController
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
	
	
	@GetMapping("/allprices")
	public ResponseEntity<List<PriceDTO>> prices(){
		ModelMapper mapper = new ModelMapper();

		return ResponseEntity.ok()
				.body(getAllPricesUseCase
					.findAll()
					.stream()
					.map((price) -> mapper.map(price, PriceDTO.class))
					.collect(Collectors.toList()));
	}
	
	@GetMapping("/price/search")
	public ResponseEntity<PriceReduce> getPrice(@RequestParam(required=true) String dateString,
			@RequestParam(required=true) long product_id, @RequestParam(required=true) long brand_id){
		
		ModelMapper mapper = new ModelMapper();
		
		Timestamp date = mapper.map(dateString, Timestamp.class);
		
		Price price = getPricesUseCase.getCorrectPrice(date, product_id, brand_id);
		PriceReduce priceReduce = mapper.map(price, PriceReduce.class);
		
		return ResponseEntity.ok()
				.body(priceReduce);
	}
	
}
