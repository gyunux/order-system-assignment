package com.test.service.order.dto;

import com.test.service.order.Order;
import com.test.service.order.OrderItem;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderCreateResponse {
    private Long orderId;
    private ContactInfoResponse contactInfo;
    private List<OrderItemResponse> items;

    private OrderCreateResponse(Order order){
        this.orderId = order.getId();
        this.contactInfo = new ContactInfoResponse(order);
        this.items = order.getOrderItems().stream()
                .map(OrderItemResponse::new)
                .collect(Collectors.toList());
    }

    public static OrderCreateResponse from(Order order){
        return new OrderCreateResponse(order);
    }

    @Getter
    public static class ContactInfoResponse{
        private String contactEmail;
        private String contactName;
        private String mobile;

        public ContactInfoResponse(Order order){
            this.contactEmail = order.getContactEmail();
            this.contactName = order.getContactName();
            this.mobile = order.getPhoneNumber();
        }
    }

    @Getter
    public static class OrderItemResponse{
        private String itemType;
        private Long itemId;

        public OrderItemResponse(OrderItem item){
            this.itemType = item.getItemType();
            this.itemId = item.getItemId();
        }
    }
}
