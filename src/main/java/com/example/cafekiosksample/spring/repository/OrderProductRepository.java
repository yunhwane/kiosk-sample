package com.example.cafekiosksample.spring.repository;

import com.example.cafekiosksample.spring.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
