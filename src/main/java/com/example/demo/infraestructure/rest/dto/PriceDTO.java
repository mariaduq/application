package com.example.demo.infraestructure.rest.dto;

import lombok.Data;

public @Data class PriceDTO {
	
	private long priceList;
	
	private String startDate;
	
	private String endDate;
	
	private long brandId;
	
	private long productId;
		
	private String price;

	public void setPrice(float price) {
		this.price = price + " EUR";
	}
}
