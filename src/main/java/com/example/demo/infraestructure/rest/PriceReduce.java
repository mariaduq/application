package com.example.demo.infraestructure.rest;

import java.sql.Timestamp;

public class PriceReduce {
	
	private long priceList;
	
	private Timestamp startDate;
	
	private Timestamp endDate;
	
	private long brandId;
	
	private long productId;
		
	private String price;
		
	public PriceReduce () {
		
	}
	
	public PriceReduce(long priceList, Timestamp startDate, Timestamp endDate, long brandId, long productId, float price,
			String curr) {
		this.setPriceList(priceList);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setBrandId(brandId);
		this.setProductId(productId);
		this.setPrice(price);
	}

	public long getPriceList() {
		return priceList;
	}

	public void setPriceList(long price_list) {
		this.priceList = price_list;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp start_date) {
		this.startDate = start_date;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp end_date) {
		this.endDate = end_date;
	}

	public long getBrandId() {
		return brandId;
	}

	public void setBrandId(long brand_id) {
		this.brandId = brand_id;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long product_id) {
		this.productId = product_id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price + " EUR";
	}
}
