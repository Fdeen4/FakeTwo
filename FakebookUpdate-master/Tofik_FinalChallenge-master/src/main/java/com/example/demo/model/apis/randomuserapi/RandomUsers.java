package com.example.demo.model.apis.randomuserapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomUsers {

    ArrayList <RandomUser> results;

    public RandomUsers() {
        this.results = new ArrayList<>();
    }

    public ArrayList<RandomUser> getResults() {
        return results;
    }

    public void setResults(ArrayList<RandomUser> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "RandomUsers{" +
                "results=" + results +
                '}';
    }
}
