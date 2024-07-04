package com.example.cafekiosksample.spring.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum SellingStatus {
    SELLING("판매중"),
    STOP_SELLING("판매중지"),
    SOLD_OUT("품절"),
    HOLD("보류");

    private final String text;

    public static List<SellingStatus> SellingStatusForDisplay() {
        return List.of(SELLING, HOLD);
    }
}
