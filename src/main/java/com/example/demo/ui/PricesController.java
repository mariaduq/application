package com.example.demo.ui;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.infraestructure.PriceRepository;
import com.example.demo.infraestructure.model.Price;

import jakarta.annotation.PostConstruct;

@RestController
public class PricesController {
	
	@Autowired
	PriceRepository priceRepository;
	
	@PostConstruct
	public void init() {
		priceRepository.save(new Price(Timestamp.valueOf("2020-06-14 00:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 1, 35455, 0, 35.50f, "EUR"));
		priceRepository.save(new Price(Timestamp.valueOf("2020-06-14 15:00:00"), Timestamp.valueOf("2020-06-14 18:30:00"), 1, 35455, 1, 25.45f, "EUR"));
		priceRepository.save(new Price(Timestamp.valueOf("2020-06-15 00:00:00"), Timestamp.valueOf("2020-06-15 11:00:00"), 1, 35455, 1, 30.50f, "EUR"));
		priceRepository.save(new Price(Timestamp.valueOf("2020-06-15 16:00:00"), Timestamp.valueOf("2020-12-31 23:59:59"), 1, 35455, 1, 38.95f, "EUR"));
	}
	
	@GetMapping("/")
	ResponseEntity<List<Price>> prices(){
		return ResponseEntity.ok().body(priceRepository.findAll());
	}
	
}
