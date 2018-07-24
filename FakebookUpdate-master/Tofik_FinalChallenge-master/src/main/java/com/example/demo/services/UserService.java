package com.example.demo.services;


import com.cloudinary.utils.ObjectUtils;
import com.example.demo.config.CloudinaryConfig;
import com.example.demo.model.Follow;
import com.example.demo.model.Profile;
import com.example.demo.model.UserPost;
import com.example.demo.model.auth.AppUser;
import com.example.demo.model.auth.AppUserRepository;
import com.example.demo.model.auth.UserRole;
import com.example.demo.model.auth.UserRoleRepository;
import com.example.demo.model.repositories.FollowRepository;
import com.example.demo.model.repositories.ProfileRepository;
import com.example.demo.model.repositories.UserPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mail.javamail.JavaMailSender;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class UserService {
    @Autowired
    AppUserRepository users;

    @Autowired
    UserRoleRepository roles;

    @Autowired
    ProfileRepository profiles;

    @Autowired
    FollowRepository friendship;

    @Autowired
    UserPostRepository posts;

    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    private JavaMailSender javaMailSender;

    public void addUser(String ausername, String apassword, String role) {
        AppUser user = new AppUser();
        user.setUsername(ausername);
        user.setPassword(apassword);
        user.addRole(roles.findByRole(role));
        users.save(user);

    Profile profile = new Profile();
        profile.setProfileOwner(user);
        profiles.save(profile);

        user.setProfile(profile);

        users.save(user);
}
    public void addUser(String ausername, String apassword, String[] roles) {
        AppUser user = new AppUser();
        user.setUsername(ausername);
        user.setPassword(apassword);
        for(String role : roles)
            user.addRole(this.roles.findByRole(role));
        users.save(user);

        Profile profile = new Profile();
        profile.setProfileOwner(user);
        profiles.save(profile);

        user.setProfile(profile);

        users.save(user);

    }

    public void addAppRole(String role) {
        UserRole newRole = new UserRole(role);
        roles.save(newRole);
    }

    public AppUser findUser(Authentication authentication){
        return users.findByUsername(authentication.getName());
    }

    public UserRole findRole(String role) {
        return roles.findByRole(role);
    }

    public void saveMe(AppUser aUser) {
        users.save(aUser);
    }

    //profiling stuff below this line

    public Profile findProfile(Authentication authentication){
        return profiles.findByProfileOwner_Username(authentication.getName());
    }public Profile findProfile(long id){
        return profiles.findById(id).get();
    }

    public void saveProfile(Profile profile, Authentication authentication){
        AppUser user = users.findByUsername(authentication.getName());
        profile.setProfileOwner(user);
        profiles.save(profile);
        users.save(user);
    }


    public AppUser findByEmail(String email){
        return users.findByEmail(email);
    }

    public AppUser findByConfirmationco(String confirmationcode){
        return users.findByConfirmationcode(confirmationcode);
    }

    public void sendEmail(SimpleMailMessage email){
        javaMailSender.send(email);
    }


/*    public void saveProfile(Profile profile, Authentication authentication, MultipartFile file){
        AppUser user = users.findByUsername(authentication.getName());
        try{
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            String uploadedName = (String)uploadResult.get("public_id");
            String transformedImage = cloudc.createUrl(uploadedName, 150, 150);
            profile.setProfilepicture();Picture(transformedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        profile.setProfileOwner(user);
        user.setProfile(profile);
        profiles.save(profile);
        users.save(user);
    }*/

    //friendship

    public Iterable<UserPost> getTimeline(Authentication authentication){
        Set<Profile> friends = (Set<Profile>)getFollowees(authentication);
        List<UserPost> timeline = new ArrayList<>();

        for (UserPost post : posts.findAllByOrderByTimePostedDesc()){
            if (friends.contains(post.getWhoPosted()) || post.getWhoPosted() == profiles.findByProfileOwner_Username(authentication.getName())){
                timeline.add(post);
            }
        }
        return timeline;
    }

    public void follow(Authentication authentication, Profile profileOfFriend){
        Profile thisUser = profiles.findByProfileOwner_Username(authentication.getName());
        if (!friendship.existsByFollowerAndFollowee(thisUser, profileOfFriend)) {
            Follow follow = new Follow();
            follow.setFollowee(profileOfFriend);
            follow.setFollower(thisUser);
            friendship.save(follow);
        }
    }public void unFollow(Authentication authentication, Profile exFriend){
        friendship.delete(friendship.findByFollower_ProfileOwner_UsernameAndFollowee(authentication.getName(), exFriend));
    }

    public Iterable<Profile> getFollowees(Authentication authentication){
        Set<Profile> followees = new HashSet<>();
        for (Follow follow : friendship.findAllByFollower_ProfileOwner_Username(authentication.getName())){
            followees.add(follow.getFollowee());
        }
        return followees;
    }public Iterable<Profile> getFollowees(long id){
        Set<Profile> followees = new HashSet<>();
        for (Follow follow : friendship.findAllByFollower_Id(id)){
            followees.add(follow.getFollowee());
        }
        return followees;
    }

    //posting

    public void addThePost(Authentication authentication, UserPost post){
        post.setWhoPosted(profiles.findByProfileOwner_Username(authentication.getName()));
        post.setTimePosted(LocalDateTime.now());
        posts.save(post);
    }
    public void addThePost(Authentication authentication, UserPost post, MultipartFile file){
        Profile thisProfile = profiles.findByProfileOwner_Username(authentication.getName());
        try{
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            String uploadedName = (String)uploadResult.get("public_id");
            String transformedImage = cloudc.createUrlUnResized(uploadedName);
            post.setImage(transformedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        post.setWhoPosted(thisProfile);
        post.setTimePosted(LocalDateTime.now());
        posts.save(post);

    thisProfile.addPost(post);
        profiles.save(thisProfile);
    }
    public void addThePost(Authentication authentication, UserPost post, MultipartFile file, String filter){
        Profile thisProfile = profiles.findByProfileOwner_Username(authentication.getName());
        try{
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            String uploadedName = (String)uploadResult.get("public_id");
            String transformedImage = cloudc.createUrlUnResized(uploadedName, filter);
            post.setImage(transformedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        post.setWhoPosted(thisProfile);
        post.setTimePosted(LocalDateTime.now());
        posts.save(post);

 thisProfile.addPost(post);
        profiles.save(thisProfile);
    }
}