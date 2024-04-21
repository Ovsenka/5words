package com.ovsenka.words.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "advert_tap_info")
public class AdvertTapInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userId;

    @JoinColumn(name = "advert_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AdvertEntity advert;

    @Column(name = "link_advert_tap_time")
    private String linkAdvertTapTime;
    public String getLinkAdvertTapTime() {
        return linkAdvertTapTime;
    }

    public void setLinkAdvertTapTime(String linkAdvertTapTime) {
        this.linkAdvertTapTime = linkAdvertTapTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public AdvertEntity getAdvert() {
        return advert;
    }

    public void setAdvert(AdvertEntity advert) {
        this.advert = advert;
    }
}
