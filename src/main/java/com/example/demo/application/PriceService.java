package com.example.demo.application;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.infraestructure.PriceAggregateDAO;
import com.example.demo.model.PriceAggregate;
import com.example.demo.ui.PriceDTO;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PriceService {
	
	private PriceAggregateDAO priceAggregateDAO;
	
	public PriceService(PriceAggregateDAO priceAggregateDAO){
		this.priceAggregateDAO=priceAggregateDAO;
	}

	public boolean validProductId(long product_id) {
		List<PriceAggregate> prices = new ArrayList<PriceAggregate>();
		prices = priceAggregateDAO.findAll();
		
		for (PriceAggregate pr : prices) {
			if(pr.getProduct_id() == product_id) return true;
		}
		return false;
	}
	
	public List<PriceDTO> validPrices(Timestamp date){
		
		ModelMapper mapper = new ModelMapper();
		
		List<PriceAggregate> prices = new ArrayList<PriceAggregate>();
		prices = priceAggregateDAO.findAll();
	
		List<PriceAggregate> validPrices = new ArrayList<PriceAggregate>();
		
		for(PriceAggregate pr : prices ) {
			if(pr.getStart_date().before(date)) {
				if(pr.getEnd_date().after(date)) {
					validPrices.add(pr);
				}
			}
		}
		return validPrices
				.stream()
				.map((price)->mapper.map(price, PriceDTO.class))
				.collect(Collectors.toList());
	}

	public float getCorrectPrice(Timestamp date, long product_id) {
		
		if (!validProductId(product_id)) throw new EntityNotFoundException();
		
		List<PriceDTO> validPrices = validPrices(date);
		
		if (validPrices.isEmpty()) throw new EntityNotFoundException();
		
		PriceDTO price = new PriceDTO();
		price = validPrices.get(0);
		
		for(PriceDTO pr1 : validPrices) {
			if(pr1.getPriority() > price.getPriority()) {
				price = pr1;
			}
		}
		return price.getPrice();
	}
	
	public List<PriceDTO> findAll(){
		ModelMapper mapper = new ModelMapper();
		
		return priceAggregateDAO
				.findAll()
				.stream()
				.map((price)->mapper.map(price, PriceDTO.class))
				.collect(Collectors.toList());
	}
	
}
