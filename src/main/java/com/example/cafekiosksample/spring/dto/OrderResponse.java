package com.example.cafekiosksample.spring.dto;

import com.example.cafekiosksample.spring.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse (
        Long id, int totalPrice, LocalDateTime registeredDateTime,
        List<ProductResponse> products

) {
}
