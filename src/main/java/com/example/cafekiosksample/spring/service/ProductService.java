package com.example.cafekiosksample.spring.service;


import com.example.cafekiosksample.spring.domain.SellingStatus;
import com.example.cafekiosksample.spring.dto.ProductResponse;
import com.example.cafekiosksample.spring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        return productRepository.findAllBySellingStatusIn(SellingStatus.SellingStatusForDisplay())
                .stream()
                .map(ProductResponse::of)
                .toList();
    }
}
