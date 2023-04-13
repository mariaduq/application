package com.example.demo.infraestructure.rest;

import java.sql.Timestamp;

public class PriceReduce {
	
	private long price_list;
	
	private Timestamp start_date;
	
	private Timestamp end_date;
	
	private long brand_id;
	
	private long product_id;
		
	private String price;
		
	public PriceReduce () {
		
	}
	
	public PriceReduce(Timestamp start_date, Timestamp end_date, long brand_id, long product_id, float price,
			String curr) {
		this.setStart_date(start_date);
		this.setEnd_date(end_date);
		this.setBrand_id(brand_id);
		this.setProduct_id(product_id);
		this.setPrice(price);
	}

	public long getPrice_list() {
		return price_list;
	}

	public void setPrice_list(long price_list) {
		this.price_list = price_list;
	}

	public Timestamp getStart_date() {
		return start_date;
	}

	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}

	public Timestamp getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Timestamp end_date) {
		this.end_date = end_date;
	}

	public long getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(long brand_id) {
		this.brand_id = brand_id;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price + " EUR";
	}
}
