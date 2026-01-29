package com.test.service.clothes;

import com.test.service.inbound.InboundStockEvent;
import com.test.service.order.strategy.StockDecreaseStrategy;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClothesService implements StockDecreaseStrategy {
    private final ClothesRepository clothesRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean supports(String itemType) {
        return "clothes".equals(itemType);
    }

    @Transactional
    public void decreaseStock(Long foodId, Long quantity) {
        Clothes clothes = clothesRepository.findByIdWithLock(foodId)
                .orElseThrow(() -> new IllegalArgumentException("해당 옷이 없습니다."));

        clothes.decreaseStock(quantity);

        if (clothes.getStock() < 10) {
            eventPublisher.publishEvent(new InboundStockEvent(
                    "clothes",
                    clothes.getName(),
                    100L
            ));
        }
    }
}
