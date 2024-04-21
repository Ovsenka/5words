package com.ovsenka.words.Repository;

import com.ovsenka.words.Entity.BotEntity;
import com.ovsenka.words.Entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<GameEntity, Long> {
    List<GameEntity> findAllByBot(BotEntity bot);
}
