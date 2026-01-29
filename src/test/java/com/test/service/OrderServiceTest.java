package com.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.test.service.food.Food;
import com.test.service.food.FoodRepository;
import com.test.service.inbound.InboundRequest;
import com.test.service.inbound.InboundRequestRepository;
import com.test.service.order.OrderService;
import com.test.service.order.dto.OrderCreateRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private InboundRequestRepository inboundRequestRepository;

    @DisplayName("91명 동시 주문과 입고요청")
    @Test
    void orderTest() throws InterruptedException {
        Food savedFood = foodRepository.save(new Food("떡볶이", 100L));
        Long foodId = savedFood.getId();

        List<Thread> workers = new ArrayList<>();

        for (int i = 0; i < 91; i++) {
            Thread thread = new Thread(() -> {
                OrderCreateRequest request = createMockRequest();
                orderService.createOrder(request);
            });
            workers.add(thread);
        }

        for (Thread worker : workers) {
            worker.start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        Food food = foodRepository.findById(foodId).get();
        assertEquals(9L, food.getStock());

        Thread.sleep(1000);

        List<InboundRequest> requests = inboundRequestRepository.findAll();

        assertFalse(requests.isEmpty());

        InboundRequest result = requests.get(0);
        assertEquals("떡볶이123", result.getEncryptedItemName());
    }

    private OrderCreateRequest createMockRequest() {
        OrderCreateRequest.ItemRequest itemRequest = new OrderCreateRequest.ItemRequest();
        itemRequest.setItemType("food");
        itemRequest.setId(1L);

        OrderCreateRequest.ContactInfoRequest contactInfo = new OrderCreateRequest.ContactInfoRequest();
        contactInfo.setContactEmail(" test@test.com");
        contactInfo.setContactName(" 구매자");
        contactInfo.setMobile("01099999999 ");

        OrderCreateRequest request = new OrderCreateRequest();
        request.setItems(itemRequest);
        request.setContactInfo(contactInfo);

        return request;
    }
}
