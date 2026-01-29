package com.test.service.order.strategy.vendor;

import org.springframework.stereotype.Component;

@Component
public class AmadonEncryptionStrategy implements VendorEncryptionStrategy {

    @Override
    public boolean supports(String itemType) {
        return "food".equalsIgnoreCase(itemType);
    }

    @Override
    public String encrypt(String itemName) {
        return itemName + "123";
    }
}
