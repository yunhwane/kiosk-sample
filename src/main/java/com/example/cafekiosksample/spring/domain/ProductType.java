package com.example.cafekiosksample.spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductType {
    HANDMADE("수제음료"),
    BOOTLE("병음료"),
    BAKERY("베이커리");

    private final String text;
}
