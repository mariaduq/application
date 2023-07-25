package com.example.demo.application.usecases.price;

import java.time.LocalDateTime;
import java.util.Comparator;

import com.example.demo.application.mapper.PriceMapper;
import com.example.demo.application.output.PriceOutput;
import com.example.demo.domain.port.PricesPort;
import com.example.demo.domain.model.Price;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetPricesUseCase {
	
	private final PricesPort pricesPort;

	private final PriceMapper priceMapper;
	
	public PriceOutput execute(LocalDateTime date, long productId) throws Exception {
	
		return pricesPort.getPrices().stream()
				.filter(price -> price.getProductId() == productId)
				.filter(price -> price.getStartDate().isBefore(date))
				.filter(price -> price.getEndDate().isAfter(date))
				.sorted(Comparator.comparingInt(Price::getPriority).reversed())
				.findFirst()
				.map(priceMapper::toOutput)
				.orElseThrow(() -> new Exception("No tenemos ningún precio para los datos introducidos. Compruébalos de nuevo."));
	}
}
