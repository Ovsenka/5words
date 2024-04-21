package com.ovsenka.words.Repository;

import com.ovsenka.words.Entity.BotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BotRepository extends JpaRepository<BotEntity, Long> {
    Optional<BotEntity> findByToken(String token);

}
