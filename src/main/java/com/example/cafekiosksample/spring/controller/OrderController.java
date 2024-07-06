package com.example.cafekiosksample.spring.controller;


import com.example.cafekiosksample.spring.dto.OrderCreateRequest;
import com.example.cafekiosksample.spring.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderCreateRequest, LocalDateTime.now()));
    }

}
