package com.example.demo.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.infraestructure.PriceRepository;
import com.example.demo.infraestructure.model.Price;

@RestController
public class PricesController {
	
	@Autowired
	PriceRepository priceRepository;
	
}
