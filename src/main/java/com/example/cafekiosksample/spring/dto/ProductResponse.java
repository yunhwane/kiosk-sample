package com.example.cafekiosksample.spring.dto;

import com.example.cafekiosksample.spring.domain.Product;
import com.example.cafekiosksample.spring.domain.SellingStatus;
import com.example.cafekiosksample.spring.domain.ProductType;

public record ProductResponse(Long id, String name, int price, ProductType type, SellingStatus sellingType) {
    public static ProductResponse of(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getType(), product.getSellingStatus());
    }
}
