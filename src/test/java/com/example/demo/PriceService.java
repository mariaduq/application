package com.example.demo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.demo.infraestructure.PriceRepository;
import com.example.demo.infraestructure.model.Price;

public class PriceService {
	
	private PriceRepository priceRepository;
	
	PriceService(PriceRepository priceRepository){
		this.priceRepository = priceRepository;
	}

	/*public void validProductId(long product_id) {
		List<Price> prices = new ArrayList<Price>();
		prices = priceRepository.findAll();
		
		Iterator<Price> it = prices.iterator();
		
	}*/

	public void getPrice(Timestamp date) {
		List<Price> prices = new ArrayList<Price>();
		prices = priceRepository.findAll();
		
		Iterator<Price> it = prices.iterator();
	}
	
	//public boolean
}
