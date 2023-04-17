package com.example.demo.model;

import java.sql.Timestamp;

public class Price {
	
	private long priceList;
	
	private Timestamp startDate;
	
	private Timestamp endDate;
	
	private long brandId;
	
	private long productId;
	
	private int priority;
	
	private float price;
	
	private String curr;
	
	public Price () {
		
	}
	
	public Price(long priceList, Timestamp startDate, Timestamp endDate, long brandId, long productId, int priority, float price,
			String curr) {
		this.setPriceList(priceList);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setBrandId(brandId);
		this.setProductId(productId);
		this.setPriority(priority);
		this.setPrice(price);
		this.setCurr(curr);
	}
	
	public long getPriceList() {
		return priceList;
	}

	public void setPriceList(long priceList) {
		this.priceList = priceList;
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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}
}
