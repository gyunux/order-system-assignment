package com.test.service.clothes;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Clothes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long stock;

    @Builder
    public Clothes(String name,Long stock){
        this.name = name;
        this.stock = stock;
    }

    public void decreaseStock(Long quantity){
        if(quantity <= 0){
            throw new IllegalArgumentException("감소할 수량은 0보다 커야합니다.");
        }

        if(this.stock - quantity < 0){
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        this.stock = this.stock - quantity;


    }
}
