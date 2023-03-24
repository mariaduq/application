package com.example.demo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.infraestructure.PriceRepository;
import com.example.demo.infraestructure.model.Price;

import jakarta.persistence.EntityNotFoundException;

public class PriceService {
	
	private PriceRepository priceRepository;
	
	PriceService(PriceRepository priceRepository){
		this.priceRepository = priceRepository;
	}

	public boolean validProductId(long product_id) {
		List<Price> prices = new ArrayList<Price>();
		prices = priceRepository.findAll();
		
		for (Price pr : prices) {
			if(pr.getProduct_id() == product_id) return true;
		}
		return false;
	}

	public Price getCorrectPrice(Timestamp date, long product_id) {
		
		if (!validProductId(product_id)) throw new EntityNotFoundException();
		
		List<Price> prices = new ArrayList<Price>();
		prices = priceRepository.findAll();
	
		List<Price> validPrices = new ArrayList<Price>();
		
		for(Price pr : prices ) {
			if(pr.getStart_date().before(date)) {
				if(pr.getEnd_date().after(date)) {
					validPrices.add(pr);
				}
			}
		}
		
		if (validPrices.isEmpty()) throw new EntityNotFoundException();
		
		Price price = new Price();
		price = validPrices.get(0);
		
		for(Price pr1 : validPrices) {
			if(pr1.getPriority() > price.getPriority()) {
				price = pr1;
			}
		}
		
		return price;
		
		//LO SIGUIENTE QUE TENGO QUE HACER ES COMPROBAR EL ID
	}
	
	//public boolean
}
