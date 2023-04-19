package com.example.demo.infraestructure.ddbb;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import com.example.demo.application.ports.PricesPort;
import com.example.demo.infraestructure.ddbb.model.PriceEntity;
import com.example.demo.model.Price;

@Service
public class PriceAdapter implements PricesPort{
	
	private PriceRepositoryJpa priceRepositoryJpa;

	public PriceAdapter (PriceRepositoryJpa priceRepositoryJpa) {
		this.priceRepositoryJpa=priceRepositoryJpa;
	}

	public List<Price> getPrices(){
		
		ModelMapper mapper = new ModelMapper();
		
		TypeMap<Timestamp, LocalDateTime> typeMap = mapper.createTypeMap(Timestamp.class, LocalDateTime.class);
		typeMap.setConverter(context -> {
		    Timestamp timestamp = context.getSource();
		    return timestamp == null ? null : timestamp.toLocalDateTime();
		});
		
		/*
		Consumer<PriceEntity> consumer = s -> {
			
			LocalDateTime startDateLDT = mapper.map(s.getStartDate(), LocalDateTime.class);
			
			Price price = mapper.map(s, Price.class);
			
			price.setStartDate(startDateLDT);
		};*/
		
		return priceRepositoryJpa
				.findAll()
				.stream()
				.map((priceEntity) -> mapper.map(priceEntity, Price.class))
				.collect(Collectors.toList());
	
	}
	
}


