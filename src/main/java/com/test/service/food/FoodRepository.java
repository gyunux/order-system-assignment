package com.test.service.food;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FoodRepository extends JpaRepository<Food, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select f from Food f where f.id = :id")
    Optional<Food> findByIdWithLock(@Param("id") Long id);
}
