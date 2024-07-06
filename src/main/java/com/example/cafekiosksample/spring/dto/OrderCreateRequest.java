package com.example.cafekiosksample.spring.dto;

import java.util.List;

public record OrderCreateRequest(
        List<String> productNumbers
) {
}
