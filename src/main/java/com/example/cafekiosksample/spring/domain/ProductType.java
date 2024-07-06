package com.example.cafekiosksample.spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum ProductType {
    HANDMADE("수제음료"),
    BOTTLE("병음료"),
    BAKERY("베이커리");

    private final String text;

    public static boolean containsProductType(ProductType productType) {
        return List.of(BOTTLE, BAKERY).contains(productType);
    }
}
