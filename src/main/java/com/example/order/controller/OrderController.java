package com.example.order.controller;

import com.example.order.request.OrderCreateRequest;
import com.example.order.response.OrderResponse;
import com.example.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/{orderNo}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderNo) {
        return ResponseEntity.ok(orderService.getOrder(orderNo));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable String userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    @PatchMapping("/{orderNo}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderNo) {
        return ResponseEntity.ok(orderService.cancelOrder(orderNo));
    }

    @PatchMapping("/{orderNo}/complete")
    public ResponseEntity<OrderResponse> completeOrder(@PathVariable Long orderNo) {
        return ResponseEntity.ok(orderService.completeOrder(orderNo));
    }
}
