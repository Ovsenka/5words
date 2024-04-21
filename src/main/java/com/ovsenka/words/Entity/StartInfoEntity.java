package com.ovsenka.words.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bot_start_info")
public class StartInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @JoinColumn(name = "bot_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private BotEntity bot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public BotEntity getBot() {
        return bot;
    }

    public void setBot(BotEntity bot) {
        this.bot = bot;
    }
}
