package com.example.demo.model.auth;

import com.example.demo.model.Friend;
import com.example.demo.model.Message;
import com.example.demo.model.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    private String password;
    private String fullname;

    @Email(message = "You need to provide a valid email")
    @NotEmpty(message = "This field can not be empty")
    private String email;

    private String confirmationcode;

    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles;

    @OneToMany(mappedBy = "sender")
    private Set<Message> inbox;

    @OneToMany(mappedBy = "recipient")
    private Set<Message> sentMsgs;

    @OneToOne
    private Profile profile;

    private boolean hasProfile = false;

    public AppUser() {
        roles = new HashSet<>();
    }

    public AppUser(String username, String password) {
        this.username = username;
        setPassword(password);
        roles = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public void addRole(UserRole theRole)
    {
        roles.add(theRole);
    }

    public Set<Message> getInbox() {
        return inbox;
    }

    public void setInbox(Set<Message> inbox) {
        this.inbox = inbox;
    }

    public void addToInbox(Message message){
        inbox.add(message);
    }

    public Set<Message> getSentMsgs() {
        return sentMsgs;
    }

    public void setSentMsgs(Set<Message> sentMsgs) {
        this.sentMsgs = sentMsgs;
    }

    public void addSentMsg(Message message){
        sentMsgs.add(message);
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmationcode() {
        return confirmationcode;
    }

    public void setConfirmationcode(String confirmationcode) {
        this.confirmationcode = confirmationcode;
    }


    public boolean hasProfile(){
        return hasProfile;
    }
    public void setHasProfile(boolean value){hasProfile = value;}

    public boolean isAdmin(){
        for (UserRole role : getRoles()){
            if (role.toString().equals("ADMIN"))
                return true;
        }
        return false;
    }
}