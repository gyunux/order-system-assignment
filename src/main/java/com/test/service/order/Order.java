package com.test.service.order;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contactEmail;

    @Column(nullable = false)
    private String contactName;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    @Builder
    public Order(String contactEmail, String contactName, String phoneNumber) {
        this.contactEmail = contactEmail;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }

    public static Order create(String contactEmail, String contactName, String phoneNumber, OrderItem... orderItems) {
        Order order = Order.builder()
                .contactEmail(contactEmail)
                .contactName(contactName)
                .phoneNumber(phoneNumber)
                .build();

        for (OrderItem item : orderItems) {
            order.addOrderItem(item);
        }

        return order;
    }
}
