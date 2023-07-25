package com.example.demo.domain.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
public @Data class Price {
	
	private long priceList;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	private Long brandId;
	
	private long productId;
	
	private int priority;
	
	private float price;
	
	private String curr;
}
