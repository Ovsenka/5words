package com.ovsenka.words.Entity;

import jakarta.persistence.*;

import java.sql.Blob;

@Entity
@Table(name = "bot")
public class BotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    @Column(name = "first_message_text")
    private String firstMsgText;
    @Column(name = "win_message_button_link")
    private String winMsgButtonLink;

    private String description;

    @Column(name = "manager_link")
    private String managerLink;

    @Column(name = "start_file_id")
    private String fileId;

    @Column(name = "start_image")
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getManagerLink() {
        return managerLink;
    }

    public void setManagerLink(String managerLink) {
        this.managerLink = managerLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstMsgText() {
        return firstMsgText;
    }

    public void setFirstMsgText(String firstMsgText) {
        this.firstMsgText = firstMsgText;
    }

    public String getWinMsgButtonLink() {
        return winMsgButtonLink;
    }

    public void setWinMsgButtonLink(String winMsgButtonLink) {
        this.winMsgButtonLink = winMsgButtonLink;
    }
}
