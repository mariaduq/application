package com.example.demo.infraestructure.ddbb.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRICES")
public class PriceEntity {
	
	@Id
	@Column(name = "PRICE_ID")
	private long price_list;
	
	@Column(name = "START_DATE")
	private Timestamp start_date;
	
	@Column(name = "END_DATE")
	private Timestamp end_date;
	
	@Column(name = "BRAND_ID")
	private long brand_id;
	
	@Column(name = "PRODUCT_ID")
	private long product_id;
	
	@Column(name = "PRIORITY")
	private int priority;
	
	@Column(name = "PRICE")
	private float price;
	
	@Column(name = "CURR")
	private String curr;
	
	public PriceEntity () {
		
	}
	
	public PriceEntity(long price_list, Timestamp start_date, Timestamp end_date, long brand_id, long product_id, int priority, float price,
			String curr) {
		this.setPrice_list(price_list);
		this.setStart_date(start_date);
		this.setEnd_date(end_date);
		this.setBrand_id(brand_id);
		this.setProduct_id(product_id);
		this.setPriority(priority);
		this.setPrice(price);
		this.setCurr(curr);
	}
	
	public long getPrice_list() {
		return price_list;
	}

	public void setPrice_list(Long price_list) {
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
