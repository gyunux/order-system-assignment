package com.test.service.global.init;

import com.test.service.clothes.Clothes;
import com.test.service.clothes.ClothesRepository;
import com.test.service.food.Food;
import com.test.service.food.FoodRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataInitializer {

    private final FoodRepository foodRepository;
    private final ClothesRepository clothesRepository;

    @PostConstruct
    public void init() {
        Food initFood = Food.builder()
                .name("떡볶이")
                .stock(100L)
                .build();

        foodRepository.save(initFood);

        Clothes initClothes = Clothes.builder()
                .name("A청바지")
                .stock(100L)
                .build();

        clothesRepository.save(initClothes);
    }
}
