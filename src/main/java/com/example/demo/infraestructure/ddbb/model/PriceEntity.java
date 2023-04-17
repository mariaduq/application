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
	
	public PriceEntity () {
		
	}
	
	public PriceEntity(long priceList, Timestamp startDate, Timestamp endDate, long brandId, long productId, int priority, float price,
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

	public void setPriceList(Long price_list) {
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
