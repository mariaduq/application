package com.example.demo.model;

import java.sql.Timestamp;

public class PriceAggregate {
	
	private long price_list;
	
	private Timestamp start_date;
	
	private Timestamp end_date;
	
	private long brand_id;
	
	private long product_id;
	
	private int priority;
	
	private float price;
	
	private String curr;
	
	public PriceAggregate(Timestamp start_date, Timestamp end_date, long brand_id, long product_id, int priority, float price,
			String curr) {
		this.start_date=start_date;
		this.end_date=end_date;
		this.brand_id=brand_id;
		this.product_id=product_id;
		this.priority=priority;
		this.price=price;
		this.curr=curr;
	}
	
	
}
