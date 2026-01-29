package com.test.service.order;

import com.test.service.clothes.ClothesService;
import com.test.service.food.FoodService;
import com.test.service.order.dto.OrderCreateRequest;
import com.test.service.order.dto.OrderCreateResponse;
import com.test.service.order.strategy.StockDecreaseStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    //재고 감소 전략패턴
    private final List<StockDecreaseStrategy> stockStrategies;

    @Transactional
    public OrderCreateResponse createOrder(OrderCreateRequest request){
        String itemType = request.getItems().getItemType();
        Long itemId = request.getItems().getId();
        String contactEmail = request.getContactInfo().getContactEmail().trim();
        String contactName = request.getContactInfo().getContactName().trim();
        String mobile = request.getContactInfo().getMobile().trim();

        //주문 요청 시 재고는 보내지않으므로 1L로 하드코딩
        // -> 향후 주문 요청 재고에 맞게 리팩토링
        decreaseStock(itemType,itemId,1L);

        OrderItem orderItem = OrderItem.builder()
                .itemType(request.getItems().getItemType())
                .itemId(request.getItems().getId())
                .build();

        Order order = Order.create(contactEmail,contactName,mobile,orderItem);

        orderRepository.save(order);

        return OrderCreateResponse.from(order);
    }

    private void decreaseStock(String itemType, Long itemId, Long quantity) {
        stockStrategies.stream()
                .filter(s -> s.supports(itemType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 상품 타입: " + itemType))
                .decreaseStock(itemId, quantity);
    }
}
