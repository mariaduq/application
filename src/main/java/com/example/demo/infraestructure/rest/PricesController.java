package com.example.demo.infraestructure.rest;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.GetPricesUseCase;
import com.example.demo.infraestructure.ddbb.PriceRepositoryJpa;
import com.example.demo.infraestructure.ddbb.model.PriceEntity;
import com.example.demo.model.Price;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/")
public class PricesController {
	
	@Autowired
	PriceRepositoryJpa priceRepository;
	
	@Autowired
	GetPricesUseCase priceService;
	
	@PostConstruct
	public void init() {
		priceRepository.save(new PriceEntity(Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 1, 35455, 0, 35.50f, "EUR"));
		priceRepository.save(new PriceEntity(Timestamp.valueOf("2020-06-14 15:00:00"), Timestamp.valueOf("2020-06-14 18:30:00"), 1, 35455, 1, 25.45f, "EUR"));
		priceRepository.save(new PriceEntity(Timestamp.valueOf("2020-06-15 00:00:00"), Timestamp.valueOf("2020-06-15 11:00:00"), 1, 35455, 1, 30.50f, "EUR"));
		priceRepository.save(new PriceEntity(Timestamp.valueOf("2020-06-15 16:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 1, 35455, 1, 38.95f, "EUR"));
	}
	
	
	@GetMapping
	public ResponseEntity<List<PriceDTO>> prices(){
		ModelMapper mapper = new ModelMapper();

		return ResponseEntity.ok()
				.body(priceService
					.findAll()
					.stream()
					.map((price) -> mapper.map(price, PriceDTO.class))
					.collect(Collectors.toList()));
	}
	
	@GetMapping("{date}/{product_id}/{brand_id}")
	public ResponseEntity<PriceDTO> getPrice(@PathVariable Timestamp date, @PathVariable long product_id, @PathVariable long brand_id){
		ModelMapper mapper = new ModelMapper();
		
		Price price = priceService.getCorrectPrice(date, product_id, brand_id);
		PriceDTO priceDTO = mapper.map(price, PriceDTO.class);
		
		return ResponseEntity.ok()
				.body(priceDTO);
	}
	
}
