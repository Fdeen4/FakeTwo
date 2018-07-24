package com.example.demo.model;

import com.example.demo.model.auth.AppUser;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Friend{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fullName;
    private String picture;
    private String lastestPost;
    private boolean follow;
    private boolean likePost;

    @OneToMany(mappedBy = "friend")
    private Set<PreviousPost>posts;

    @OneToMany(mappedBy = "whoPosted")
    private Set<UserPost> post;

    @ManyToOne
    Profile profile;

    public Friend() {
        this.posts = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLastestPost() {
        return lastestPost;
    }

    public void setLastestPost(String lastestPost) {
        this.lastestPost = lastestPost;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public boolean isLikePost() {
        return likePost;
    }

    public void setLikePost(boolean likePost) {
        this.likePost = likePost;
    }

    public Set<PreviousPost> getPosts() {
        return posts;
    }

    public void setPosts(Set<PreviousPost> posts) {
        this.posts = posts;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
