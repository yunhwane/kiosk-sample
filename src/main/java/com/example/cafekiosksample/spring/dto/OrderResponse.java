package com.example.cafekiosksample.spring.dto;

import com.example.cafekiosksample.spring.domain.Order;
import com.example.cafekiosksample.spring.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse (
        Long id, int totalPrice, LocalDateTime registeredDateTime,
        List<ProductResponse> products

) {
    public static OrderResponse of(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalPrice(),
                order.getRegisteredDateTime(),
                ProductResponse.listOf(order.getOrderProducts())
        );
    }
}
