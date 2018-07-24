package com.example.demo.model;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDateTime followedAt;

    @ManyToOne
    private Profile follower;

    @ManyToOne
    private Profile followee;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getFollowedAt() {
        return followedAt;
    }

    public Profile getFollower() {
        return follower;
    }

    public void setFollower(Profile follower) {
        this.follower = follower;
    }

    public Profile getFollowee() {
        return followee;
    }

    public void setFollowee(Profile followee) {
        this.followee = followee;
        followedAt = LocalDateTime.now();
    }
}
