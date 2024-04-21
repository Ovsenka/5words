package com.ovsenka.words.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "game")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userId;
    @JoinColumn(name = "bot_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private BotEntity bot;
    @JoinColumn(name = "prize_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrizeEntity prize;
    private String result;
    @Column(name = "result_time")
    private String resultTime;

    @Column(name = "game_index")
    private int gameIndex;

    @Column(name = "first_start_game_time")
    private String firstStartGameTime;

    public int getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(int gameIndex) {
        this.gameIndex = gameIndex;
    }

    public String getFirstStartGameTime() {
        return firstStartGameTime;
    }

    public void setFirstStartGameTime(String firstStartGameTime) {
        this.firstStartGameTime = firstStartGameTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public BotEntity getBot() {
        return bot;
    }

    public void setBot(BotEntity bot) {
        this.bot = bot;
    }

    public PrizeEntity getPrize() {
        return prize;
    }

    public void setPrize(PrizeEntity prize) {
        this.prize = prize;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultTime() {
        return resultTime;
    }

    public void setResultTime(String resultTime) {
        this.resultTime = resultTime;
    }
}
