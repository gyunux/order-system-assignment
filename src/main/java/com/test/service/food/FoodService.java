package com.test.service.food;

import com.test.service.inbound.InboundStockEvent;
import com.test.service.order.strategy.StockDecreaseStrategy;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService implements StockDecreaseStrategy {
    private final FoodRepository foodRepository;
    //이벤트 발행
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean supports(String itemType) {
        return "food".equals(itemType);
    }

    @Override
    @Transactional
    public void decreaseStock(Long itemId, Long quantity) {
        Food food = foodRepository.findByIdWithLock(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 음식이 없습니다."));

        food.decreaseStock(quantity);

        //향후 입고 요청 수량은 getRequiredStock으로 처리
        if (food.getStock() < 10) {
            eventPublisher.publishEvent(new InboundStockEvent(
                    "food",
                    food.getName(),
                    100L
            ));
        }
    }
}
