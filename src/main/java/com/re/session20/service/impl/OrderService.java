package com.re.session20.service.impl;


import com.re.session20.model.dto.request.OrderSummaryDTO;

public interface OrderService {
    OrderSummaryDTO getMySummary(Long userId);
}