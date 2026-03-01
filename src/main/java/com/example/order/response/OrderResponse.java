package com.example.order.response;

import com.example.order.entity.OrderEntity;
import com.example.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class OrderResponse {
    private Long orderNo;
    private String userId;
    private String userName;
    private String userEmail;
    private String productName;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public static OrderResponse of(OrderEntity order, String userName, String userEmail) {
        return OrderResponse.builder()
                .orderNo(order.getOrderNo())
                .userId(order.getUserId())
                .userName(userName)
                .userEmail(userEmail)
                .productName(order.getProductName())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .totalPrice(order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())))
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }

    // 유저 정보 없이 주문 목록 반환할 때 사용
    public static OrderResponse from(OrderEntity order) {
        return of(order, null, null);
    }
}
