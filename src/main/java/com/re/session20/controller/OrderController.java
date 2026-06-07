package com.re.session20.controller;

import com.re.session20.model.dto.request.OrderSummaryDTO;
import com.re.session20.model.entity.AppUser;
import com.re.session20.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/my-summary")
    public ResponseEntity<OrderSummaryDTO> getMySummary() {

        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(orderService.getMySummary(currentUser.getId()));
    }
}