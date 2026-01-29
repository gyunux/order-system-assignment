package com.test.service.order.strategy;

public interface StockDecreaseStrategy {
    boolean supports(String itemType);
    void decreaseStock(Long itemId,Long quantity);
}
