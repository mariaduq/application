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

	public Price getCorrectPrice(Timestamp date) {
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
		
		Price price = new Price();
		price = validPrices.get(0);
		
		for(Price pr1 : validPrices) {
			if(pr1.getPriority() > price.getPriority()) {
				price = pr1;
			}
		}
		
		return price;
	}
	
	//public boolean
}
