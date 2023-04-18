package com.example.demo.infraestructure.rest;

import java.sql.Timestamp;

import lombok.Data;

public @Data class PriceDTO {
	
	private long priceList;
	
	private Timestamp startDate;
	
	private Timestamp endDate;
	
	private long brandId;
	
	private long productId;
		
	private String price;

	public void setPrice(float price) {
		this.price = price + " EUR";
	}
}
