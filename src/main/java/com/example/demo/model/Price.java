package com.example.demo.model;

import java.sql.Timestamp;

import lombok.Data;

public @Data class Price {
	
	private long priceList;
	
	private Timestamp startDate;
	
	private Timestamp endDate;
	
	private long brandId;
	
	private long productId;
	
	private int priority;
	
	private float price;
	
	private String curr;
}
