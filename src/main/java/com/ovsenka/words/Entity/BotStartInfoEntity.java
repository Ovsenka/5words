package com.ovsenka.words.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bot_start_info")
public class BotStartInfoEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @JoinColumn(name = "bot_id")
        @ManyToOne(fetch = FetchType.LAZY)
        private BotEntity bot;
        @JoinColumn(name = "user_id")
        @ManyToOne(fetch = FetchType.LAZY)
        private UserEntity user;

        @Column(name = "bot_start_time")
        private String botStartTime;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public BotEntity getBot() {
                return bot;
        }

        public void setBot(BotEntity bot) {
                this.bot = bot;
        }

        public UserEntity getUser() {
                return user;
        }

        public void setUser(UserEntity user) {
                this.user = user;
        }

        public String getBotStartTime() {
                return botStartTime;
        }

        public void setBotStartTime(String botStartTime) {
                this.botStartTime = botStartTime;
        }
}
