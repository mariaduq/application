package com.example.demo.infraestructure.ui;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.GetPricesUseCase;
import com.example.demo.infraestructure.PriceRepositoryJpa;
import com.example.demo.infraestructure.model.PriceEntity;

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
		return ResponseEntity.ok().body(priceService.findAll());
	}
	
	@GetMapping("{date}/{product_id}/{brand_id}")
	public ResponseEntity<Float> getPrice(@PathVariable Timestamp date, @PathVariable long product_id, @PathVariable long brand_id){
		
		return ResponseEntity.ok().body(priceService.getCorrectPrice(date, product_id, brand_id));
	}
	
}
