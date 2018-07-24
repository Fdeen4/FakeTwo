package com.example.demo.model.apis.randomuserapi;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Map;

//Ignore unknown json properties - if the field exists and you don't want it in your model
@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomUser {

    @JsonProperty("login")  //Use an object called login to populate login details
    private Map<String,String> loginDetails;

    private String gender;
    private Map<String, String> name;
    private String username;

    private String unEncryptedPassword;

    @Transient //Do not save this property as a field in the database
    @JsonProperty("picture") //Use an object called picture to populate picture details
    private Map<String,String> image;

    private String imageURL;

    private String email;
    private Map<String, String> dob;
    private Location location;

    public RandomUser() {

    }

    public Map<String, String> getLoginDetails() {
        return loginDetails;
    }

    public void setLoginDetails(Map<String, String> loginDetails) {

//      Once the login details have been retrieved, save the corresponding values to 'username' and 'password' fields
        this.loginDetails = loginDetails;
        setUsername(this.loginDetails.get("username"));
        setUnEncryptedPassword(this.loginDetails.get("password"));
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUnEncryptedPassword() {
        return unEncryptedPassword;
    }

    public void setUnEncryptedPassword(String unEncryptedPassword) {
        this.unEncryptedPassword = unEncryptedPassword;
    }

    public Map<String, String> getImage() {
        return image;
    }

    public void setImage(Map<String, String> image) {
        this.image = image;
        setImageURL(image.get("large"));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Map<String, String> getDob() {
        return dob;
    }

    public void setDob(Map<String, String> dob) {
        this.dob = dob;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "RandomUser{" +
                "loginDetails=" + loginDetails +
                ", unEncryptedPassword='" + unEncryptedPassword + '\'' +
                ", image=" + image +
                ", imageURL='" + imageURL + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", location=" + location +
                '}';
    }
}