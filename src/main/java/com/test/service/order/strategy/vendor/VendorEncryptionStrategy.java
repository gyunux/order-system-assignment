package com.test.service.order.strategy.vendor;

public interface VendorEncryptionStrategy {

    boolean supports(String itemType);

    String encrypt(String itemName);
}
