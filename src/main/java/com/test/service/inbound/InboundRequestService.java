package com.test.service.inbound;

import com.test.service.order.strategy.vendor.VendorEncryptionStrategy;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InboundRequestService {

    private final InboundRequestRepository inboundRequestRepository;
    private final List<VendorEncryptionStrategy> encryptionStrategies;

    @Transactional
    public void createInboundRequest(String itemType, String itemName, Long quantity) {
        VendorEncryptionStrategy strategy = encryptionStrategies.stream()
                .filter(s -> s.supports(itemType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 벤더 타입입니다: " + itemType));

        String encryptedName = strategy.encrypt(itemName);

        InboundRequest request = InboundRequest.builder()
                .itemName(itemName)
                .encryptedItemName(encryptedName)
                .requestStock(quantity)
                .build();

        inboundRequestRepository.save(request);
    }
}
