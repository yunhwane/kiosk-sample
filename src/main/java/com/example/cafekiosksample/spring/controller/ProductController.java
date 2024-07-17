package com.example.cafekiosksample.spring.controller;


import com.example.cafekiosksample.spring.dto.ProductAddRequest;
import com.example.cafekiosksample.spring.dto.ProductResponse;
import com.example.cafekiosksample.spring.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/selling")
    public List<ProductResponse> getSellingProducts() {
        return productService.getSellingProducts();
    }

    @PostMapping("/selling")
    public void addSellingProducts(@RequestBody ProductAddRequest productAddRequest) {

        productService.addSellingProducts(productAddRequest);
    }


}
