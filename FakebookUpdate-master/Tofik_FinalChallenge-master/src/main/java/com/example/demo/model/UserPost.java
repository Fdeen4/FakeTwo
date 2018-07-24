package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserPost extends PreviousPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDateTime timePosted;
    private String text;
    private String image;

    @ManyToOne
    private Profile whoPosted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(LocalDateTime timePosted) {
        this.timePosted = timePosted;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Profile getWhoPosted() {
        return whoPosted;
    }

    public void setWhoPosted(Profile whoPosted) {
        this.whoPosted = whoPosted;
    }
}