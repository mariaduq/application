package com.example.demo.application.output;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class PriceOutput {

    private long priceList;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long brandId;

    private long productId;

    private int priority;

    private float price;

    private String curr;
}
