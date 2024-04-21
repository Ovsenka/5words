package com.ovsenka.words.Repository;

import com.ovsenka.words.Entity.BotEntity;
import com.ovsenka.words.Entity.StartInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StartInfoRepository extends JpaRepository<StartInfoEntity, Long> {
    List<StartInfoEntity> findAllByBot(BotEntity bot);
}
