package com.test.service.inbound;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockEventListener {

    private final InboundRequestService inboundRequestService;

    @Async
    @EventListener
    public void handleLowStockEvent(InboundStockEvent event) {
        inboundRequestService.createInboundRequest(
                event.getItemType(),
                event.getName(),
                event.getRequiredStock()
        );
    }
}
