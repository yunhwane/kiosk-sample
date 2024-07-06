package com.example.cafekiosksample.spring.service;


import com.example.cafekiosksample.spring.domain.Order;
import com.example.cafekiosksample.spring.domain.Product;
import com.example.cafekiosksample.spring.dto.OrderCreateRequest;
import com.example.cafekiosksample.spring.dto.OrderResponse;
import com.example.cafekiosksample.spring.repository.OrderRepository;
import com.example.cafekiosksample.spring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest orderCreateRequest, LocalDateTime registeredDateTime) {
        List<String> productNumbers = orderCreateRequest.productNumbers();
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }
}
