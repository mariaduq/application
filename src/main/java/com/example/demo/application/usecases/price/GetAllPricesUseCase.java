package com.example.demo.application.usecases.price;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.application.mapper.PriceMapper;
import com.example.demo.application.output.PriceOutput;
import com.example.demo.domain.port.PricesPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllPricesUseCase {
	
	private final PricesPort pricesPort;

	private final PriceMapper priceMapper;

	public List<PriceOutput> execute(){
		return pricesPort
				.getPrices()
				.stream()
				.map(priceMapper::toOutput)
				.collect(Collectors.toList());
	}
}
