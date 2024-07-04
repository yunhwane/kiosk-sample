package com.example.cafekiosksample.spring.repository;

import com.example.cafekiosksample.spring.domain.Product;
import com.example.cafekiosksample.spring.domain.SellingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllBySellingStatusIn(List<SellingStatus> sellingStatuses);
}
