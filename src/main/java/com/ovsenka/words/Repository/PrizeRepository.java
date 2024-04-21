package com.ovsenka.words.Repository;

import com.ovsenka.words.Entity.BotEntity;
import com.ovsenka.words.Entity.PrizeEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface PrizeRepository extends JpaRepository<PrizeEntity, Long> {
    List<PrizeEntity> findAllByBot(BotEntity bot);



}
