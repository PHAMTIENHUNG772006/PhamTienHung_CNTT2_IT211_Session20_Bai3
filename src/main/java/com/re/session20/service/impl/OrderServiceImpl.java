package com.re.session20.service.impl;
import com.re.session20.model.dto.request.OrderSummaryDTO;
import com.re.session20.model.entity.OrderStatus;
import com.re.session20.model.entity.PurchaseOrder;
import com.re.session20.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final PurchaseOrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public OrderSummaryDTO getMySummary(Long userId) {
        List<PurchaseOrder> rawOrders = orderRepository.findAllByUserId(userId);

        double totalSpent = rawOrders.stream()
                .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                .flatMap(order -> order.getOrderItems().stream())
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();


        List<String> purchasedItems = rawOrders.stream()
                .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                .flatMap(order -> order.getOrderItems().stream())
                .map(item -> item.getProduct().getName())
                .distinct()
                .collect(Collectors.toList());

        return OrderSummaryDTO.builder()
                .totalSpent(totalSpent)
                .purchasedItems(purchasedItems)
                .build();
    }
}