package com.example.demo.model;

import javax.persistence.*;

@Entity
public class PreviousPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String postDescription;

    @ManyToOne
    Profile profile;

    @ManyToOne
    Friend friend;

    public PreviousPost() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }
}
