package com.example.cafekiosksample.spring.dto;

import com.example.cafekiosksample.spring.domain.Product;
import com.example.cafekiosksample.spring.domain.ProductType;
import com.example.cafekiosksample.spring.domain.SellingStatus;

public record ProductAddRequest(
        ProductType productType,
        SellingStatus sellingStatus,
        String name,
        int price
)
{
    public Product toEntity(String productNumber) {
        return Product.builder()
                .productNumber(productNumber)
                .type(productType)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}
