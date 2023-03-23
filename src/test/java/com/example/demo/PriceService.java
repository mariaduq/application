package com.example.demo;

import com.example.demo.infraestructure.PriceRepository;

public class PriceService {
	
	private PriceRepository priceRepository;
	
	PriceService(PriceRepository priceRepository){
		this.priceRepository = priceRepository;
	}
}
