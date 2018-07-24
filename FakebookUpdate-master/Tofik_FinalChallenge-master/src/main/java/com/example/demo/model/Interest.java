package com.example.demo.model;

import javax.persistence.*;

@Entity
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Profile profile;

    private String interestDescription;

    public Interest() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getInterestDescription() {
        return interestDescription;
    }

    public void setInterestDescription(String interestDescription) {
        this.interestDescription = interestDescription;
    }
}
