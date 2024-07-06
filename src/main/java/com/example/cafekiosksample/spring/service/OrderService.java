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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest orderCreateRequest, LocalDateTime registeredDateTime) {
        List<String> productNumbers = orderCreateRequest.productNumbers();
        List<Product> products = findProducts(productNumbers);

        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private List<Product> findProducts(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);


        Map<String, Object> productMap = products.stream().collect(Collectors.toMap(Product::getProductNumber, p -> p));

        List<Product> duplicateProducts = productNumbers.stream()
                .map(productMap::get)
                .map(Product.class::cast)
                .toList();

        return duplicateProducts;
    }
}
