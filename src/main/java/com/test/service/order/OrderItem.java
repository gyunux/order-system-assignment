package com.test.service.order;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private String itemType;

    @Column(nullable = false)
    private Long itemId;

    @Builder
    public OrderItem(String itemType, Long itemId) {
        this.itemType = itemType;
        this.itemId = itemId;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
