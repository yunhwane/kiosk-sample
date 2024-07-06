package com.example.cafekiosksample.spring.service;

import com.example.cafekiosksample.spring.domain.Product;
import com.example.cafekiosksample.spring.domain.ProductType;
import com.example.cafekiosksample.spring.domain.SellingStatus;
import com.example.cafekiosksample.spring.domain.Stock;
import com.example.cafekiosksample.spring.dto.OrderCreateRequest;
import com.example.cafekiosksample.spring.dto.OrderResponse;
import com.example.cafekiosksample.spring.repository.OrderProductRepository;
import com.example.cafekiosksample.spring.repository.OrderRepository;
import com.example.cafekiosksample.spring.repository.ProductRepository;
import com.example.cafekiosksample.spring.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    StockRepository stockRepository;
    /**
     * 테스트 종료 후 데이터 초기화
     */

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("주문 리스트를 받아서 주문을 생성한다.")
    void createOrder() {
        // given
        Product product1 = createProduct(ProductType.HANDMADE, "001", 1500);
        Product product2 = createProduct(ProductType.HANDMADE, "002", 3500);
        Product product3 = createProduct(ProductType.HANDMADE, "003", 9000);

        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest orderCreateRequest = createOrderCreateRequest();
        LocalDateTime registeredDateTime = LocalDateTime.now();
        // when
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest,registeredDateTime);
        // then
        assertThat(orderResponse.id()).isNotNull();
        assertThat(orderResponse)
                .extracting(
                "registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 15500);

        assertThat(orderResponse.products())
                .hasSize(4)
                .extracting("productNumber", "price")
                .contains(
                        tuple("001", 1500),
                        tuple("001", 1500),
                        tuple("002", 3500),
                        tuple("003", 9000)
                );
    }


    @Test
    @DisplayName("재고가 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다.")
    void createOrderWithStock() {
        // given
        Product product1 = createProduct(ProductType.BOTTLE, "001", 1000);
        Product product2 = createProduct(ProductType.BAKERY, "002", 3000);
        Product product3 = createProduct(ProductType.HANDMADE, "003", 5000);

        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = Stock.create("001", 2);
        Stock stock2 = Stock.create("002", 2);

        stockRepository.saveAll(List.of(stock1, stock2));


        OrderCreateRequest orderCreateRequest = createOrderCreateRequest();
        LocalDateTime registeredDateTime = LocalDateTime.now();
        // when
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest,registeredDateTime);
        // then
        assertThat(orderResponse.id()).isNotNull();
        assertThat(orderResponse)
                .extracting(
                        "registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 10000);

        assertThat(orderResponse.products())
                .hasSize(4)
                .extracting("productNumber", "price")
                .contains(
                        tuple("001", 1000),
                        tuple("001", 1000),
                        tuple("002", 3000),
                        tuple("003", 5000)
                );

        List<Stock> stocks = stockRepository.findAll();

        assertThat(stocks)
                .hasSize(2)
                .extracting("productNumber", "quantity")
                .contains(tuple("001", 0), tuple("002", 1));
    }

    @Test
    @DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
    void createOrderWithNoStock() {
        // given
        Product product1 = createProduct(ProductType.BOTTLE, "001", 1000);
        Product product2 = createProduct(ProductType.BAKERY, "002", 3000);
        Product product3 = createProduct(ProductType.HANDMADE, "003", 5000);

        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 1);

        stockRepository.saveAll(List.of(stock1, stock2));


        OrderCreateRequest orderCreateRequest = createOrderCreateRequest();
        LocalDateTime registeredDateTime = LocalDateTime.now();

        // then
        assertThatThrownBy(() -> orderService.createOrder(orderCreateRequest,registeredDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족한 상품이 있습니다.");
    }


    @Test
    @DisplayName("중복되는 상품 번호 리스트로 주문을 생성할 수 있다.")
    void createOrderWithDuplicatedProductNumber() {
        // given
        Product product1 = createProduct(ProductType.HANDMADE, "001", 1500);
        Product product2 = createProduct(ProductType.HANDMADE, "002", 3500);
        Product product3 = createProduct(ProductType.HANDMADE, "003", 9000);

        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(List.of("001", "002", "001"));

        LocalDateTime registeredDateTime = LocalDateTime.now();
        // when
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest,registeredDateTime);
        // then
        assertThat(orderResponse.id()).isNotNull();
        assertThat(orderResponse)
                .extracting(
                        "registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 6500);

        assertThat(orderResponse.products())
                .hasSize(3)
                .extracting("productNumber", "price")
                .contains(
                        tuple("001", 1500),
                        tuple("002", 3500),
                        tuple("001", 1500)
                );
    }

    private OrderCreateRequest createOrderCreateRequest() {
        return new OrderCreateRequest(List.of("001", "001", "002", "003"));
    }


    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .name("아메리카노")
                .price(price)
                .type(type)
                .productNumber(productNumber)
                .sellingStatus(SellingStatus.SELLING)
                .build();
    }
}