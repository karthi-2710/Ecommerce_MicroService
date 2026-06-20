package com.example.app.order.Controller;

import com.example.app.order.Service.OrderService;
import com.example.app.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-User-ID") String studentId) {
        return orderService.createOrder(studentId).map(
                orderResponse -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(orderResponse)).orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
