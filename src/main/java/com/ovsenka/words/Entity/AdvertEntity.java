package com.ovsenka.words.Entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "advert")
public class AdvertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "bot_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private BotEntity bot;

    @Column(name = "advert_link")
    private String advertLink;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BotEntity getBot() {
        return bot;
    }

    public void setBot(BotEntity bot) {
        this.bot = bot;
    }

    public String getAdvertLink() {
        return advertLink;
    }

    public void setAdvertLink(String advertLink) {
        this.advertLink = advertLink;
    }
}
