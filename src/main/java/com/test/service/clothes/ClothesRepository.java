package com.test.service.clothes;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Clothes c where c.id = :id")
    Optional<Clothes> findByIdWithLock(@Param("id") Long id);
}
