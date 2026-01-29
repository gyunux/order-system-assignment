package com.test.service.order.strategy.vendor;

import org.springframework.stereotype.Component;

@Component
public class CoumangEncryptionStrategy implements VendorEncryptionStrategy {
    @Override
    public boolean supports(String itemType) {
        return "clothes".equalsIgnoreCase(itemType);
    }

    @Override
    public String encrypt(String itemName) {
        return "123" + itemName;
    }
}

