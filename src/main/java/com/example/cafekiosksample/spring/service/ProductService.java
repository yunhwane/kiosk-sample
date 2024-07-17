package com.example.cafekiosksample.spring.service;


import com.example.cafekiosksample.spring.domain.Product;
import com.example.cafekiosksample.spring.domain.ProductType;
import com.example.cafekiosksample.spring.domain.SellingStatus;
import com.example.cafekiosksample.spring.dto.ProductAddRequest;
import com.example.cafekiosksample.spring.dto.ProductResponse;
import com.example.cafekiosksample.spring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * readOnly = true : 읽기전용
 * CRUD 작업에서 CUD 작업이 이루어지지 않는 서비스 메서드에 사용 Only read
 * JPA에서 1차 캐시 스냅샷에 저장해서 변경감지 작업 진행함
 * readOnly = true로 설정하면 변경감지를 하지 않아서 약간의 성능 향상이 있음
 * CQRS : Command Query Responsibility Segregation
 * 명령과 조회를 분리한다는 의미
 * 명령 : 데이터를 변경하는 작업
 * 조회 : 데이터를 조회하는 작업
 *
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;


    public List<ProductResponse> getSellingProducts() {
        return productRepository.findAllBySellingStatusIn(SellingStatus.SellingStatusForDisplay())
                .stream()
                .map(ProductResponse::of)
                .toList();
    }


    @Transactional
    public ProductResponse addSellingProducts(ProductAddRequest productAddRequest) {
        String createProductNumber = createProductNumber();

        Product product = productAddRequest.toEntity(createProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    private String createProductNumber() {
        String latestProductNumber =  productRepository.findLatestProductNumber();
        if (latestProductNumber == null) {
            return "001";
        }
        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        return String.format("%03d", latestProductNumberInt + 1);
    }

}
