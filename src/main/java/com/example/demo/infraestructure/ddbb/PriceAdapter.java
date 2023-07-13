package com.example.demo.infraestructure.ddbb;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import com.example.demo.domain.port.PricesPort;
import com.example.demo.domain.model.Price;

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
		
		return priceRepositoryJpa
				.findAll()
				.stream()
				.map((priceEntity) -> mapper.map(priceEntity, Price.class))
				.collect(Collectors.toList());
	
	}

	@Override
	public List<Price> getProductPrices(Long productId) {

		ModelMapper mapper = new ModelMapper();

		TypeMap<Timestamp, LocalDateTime> typeMap = mapper.createTypeMap(Timestamp.class, LocalDateTime.class);
		typeMap.setConverter(context -> {
			Timestamp timestamp = context.getSource();
			return timestamp == null ? null : timestamp.toLocalDateTime();
		});

		return priceRepositoryJpa
				.findAll()
				.stream()
				.filter(priceEntity -> priceEntity.getProductId() == productId)
				.map((priceEntity) -> mapper.map(priceEntity, Price.class))
				.collect(Collectors.toList());
	}

}


