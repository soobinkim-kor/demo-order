package com.example.order.service;

import com.example.order.entity.OrderEntity;
import com.example.order.entity.OrderStatus;
import com.example.order.global.error.BusinessException;
import com.example.order.global.error.OrderErrorCode;
import com.example.order.grpc.UserGrpcClient;
import com.example.order.repository.OrderRepository;
import com.example.order.request.OrderCreateRequest;
import com.example.order.response.OrderResponse;
import com.soobin.user.grpc.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserGrpcClient userGrpcClient;

    /**
     * 주문 생성 - gRPC로 user 서비스에서 유저 검증 후 주문 저장
     */
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {
        // gRPC로 유저 존재 여부 확인
        UserResponse user = userGrpcClient.getUser(request.getUserId());
        log.debug("주문 생성 - userId: {}, userName: {}", user.getUserId(), user.getName());

        OrderEntity order = OrderEntity.builder()
                .userId(request.getUserId())
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .status(OrderStatus.PENDING)
                .build();

        OrderEntity saved = orderRepository.save(order);
        return OrderResponse.of(saved, user.getName(), user.getEmail());
    }

    /**
     * 주문 단건 조회 - gRPC로 유저 정보 포함
     */
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderNo) {
        OrderEntity order = findOrderOrThrow(orderNo);
        UserResponse user = userGrpcClient.getUser(order.getUserId());
        return OrderResponse.of(order, user.getName(), user.getEmail());
    }

    /**
     * 특정 유저의 주문 목록 조회
     */
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUser(String userId) {
        // 유저 존재 여부 확인
        userGrpcClient.getUser(userId);

        return orderRepository.findByUserIdAndIsDeletedFalse(userId)
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public OrderResponse cancelOrder(Long orderNo) {
        OrderEntity order = findOrderOrThrow(orderNo);
        order.cancel();
        return OrderResponse.from(order);
    }

    /**
     * 주문 완료 처리
     */
    @Transactional
    public OrderResponse completeOrder(Long orderNo) {
        OrderEntity order = findOrderOrThrow(orderNo);
        order.complete();
        return OrderResponse.from(order);
    }

    private OrderEntity findOrderOrThrow(Long orderNo) {
        return orderRepository.findById(orderNo)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() -> new BusinessException(OrderErrorCode.ORDER_NOT_FOUND));
    }
}
