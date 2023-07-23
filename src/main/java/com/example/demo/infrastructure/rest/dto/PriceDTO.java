package com.example.demo.infrastructure.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class PriceDTO {
	
	private long priceList;
	
	private String startDate;
	
	private String endDate;
	
	private long brandId;
	
	private long productId;

	private int priority;

	private String price;

	public void setPrice(float price) {
		this.price = price + " €";
	}
}
