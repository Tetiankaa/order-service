package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.model.Order;
import lombok.RequiredArgsConstructor;
import org.example.rest.ProductApi;
import org.example.rest.ProductDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final AtomicLong ORDER_ID = new AtomicLong(0);
    private static final Map<Long, Order> ORDERS = new HashMap<>();


    private final ProductApi productApi;

    public OrderDto createOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setId(ORDER_ID.getAndIncrement());
        order.setTimestamp(System.currentTimeMillis());

        List<ProductDto> products = productApi.getProducts();

        List<ProductDto> existingProducts = orderDto
                .getProducts()
                .stream()
                .flatMap(productName -> products.stream().filter(productDto -> Objects.equals(productDto.getName(), productName)))
                .toList();

        order.setProductIds(existingProducts.stream().map(ProductDto::getId).toList());

        ORDERS.put(order.getId(), order);

        return OrderDto
                .builder()
                .id(order.getId())
                .timestamp(order.getTimestamp())
                .products(existingProducts.stream().map(ProductDto ::getName).toList())
                .build();

    }

    public List<OrderDto> getOrders(){
        return ORDERS.values()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private OrderDto toDto(Order order){
        List<ProductDto> products = productApi.getProducts();
        System.out.println(products);

        List<String> existingProductsName = order.getProductIds()
                .stream()
                .flatMap(productId -> products.stream().filter(productDto -> Objects.equals(productDto.getId(), productId)))
                .map(ProductDto::getName)
                .toList();

        return OrderDto.builder()
                .id(order.getId())
                .timestamp(order.getTimestamp())
                .products(existingProductsName)
                .build();
    }


}
