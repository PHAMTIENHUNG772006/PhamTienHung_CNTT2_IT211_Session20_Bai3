package com.re.session20.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderSummaryDTO {
    @JsonProperty("total_spent")
    private double totalSpent;

    @JsonProperty("purchased_items")
    private List<String> purchasedItems;
}