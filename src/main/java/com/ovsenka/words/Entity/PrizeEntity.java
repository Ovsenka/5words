package com.ovsenka.words.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "prize")
public class PrizeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "bot_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private BotEntity bot;

    private Long number;

    private String title;

    private String term;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "image")
    private byte[] file;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

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

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
