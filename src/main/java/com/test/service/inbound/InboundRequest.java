package com.test.service.inbound;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InboundRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String encryptedItemName;

    @Column(nullable = false)
    private Long requestStock;

    @Builder
    public InboundRequest(String itemName, String encryptedItemName, Long requestStock) {
        this.itemName = itemName;
        this.encryptedItemName = encryptedItemName;
        this.requestStock = requestStock;
    }
}
