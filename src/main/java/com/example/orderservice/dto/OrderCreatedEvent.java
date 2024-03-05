package com.example.orderservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreatedEvent {

    private Long orderId;
    private List<Long> productIds;
}
