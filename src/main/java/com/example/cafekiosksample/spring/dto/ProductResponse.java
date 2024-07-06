package com.example.cafekiosksample.spring.dto;

import com.example.cafekiosksample.spring.domain.OrderProduct;
import com.example.cafekiosksample.spring.domain.Product;
import com.example.cafekiosksample.spring.domain.SellingStatus;
import com.example.cafekiosksample.spring.domain.ProductType;

import java.util.List;

public record ProductResponse(Long id, String name, String productNumber, int price, ProductType type, SellingStatus sellingType) {

    public static ProductResponse of(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getProductNumber(),
                product.getPrice(),
                product.getType(),
                product.getSellingStatus());
    }

    public static List<ProductResponse> listOf(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
                .toList();
    }
}
