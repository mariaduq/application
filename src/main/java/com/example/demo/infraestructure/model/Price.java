package com.example.demo.infraestructure.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Price {
	
	@Id
	private long price_list;
}
