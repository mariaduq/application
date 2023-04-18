package com.example.demo.infraestructure.ddbb.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRICES")
@AllArgsConstructor
@NoArgsConstructor
public @Data class PriceEntity {
	
	@Id
	@Column(name = "PRICE_ID")
	private long priceList;
	
	@Column(name = "START_DATE")
	private Timestamp startDate;
	
	@Column(name = "END_DATE")
	private Timestamp endDate;
	
	@Column(name = "BRAND_ID")
	private long brandId;
	
	@Column(name = "PRODUCT_ID")
	private long productId;
	
	@Column(name = "PRIORITY")
	private int priority;
	
	@Column(name = "PRICE")
	private float price;
	
	@Column(name = "CURR")
	private String curr;
}
