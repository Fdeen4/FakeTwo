package com.example.demo.model.auth;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> users;

    public UserRole() {
        users = new HashSet<>();
    }

    public UserRole(String role) {
        this.role = role;
        users = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<AppUser> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return role;
    }
}