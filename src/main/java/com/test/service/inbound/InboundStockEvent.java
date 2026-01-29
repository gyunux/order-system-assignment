package com.test.service.inbound;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InboundStockEvent {
    private String itemType;
    private String name;
    private Long requiredStock;
}
