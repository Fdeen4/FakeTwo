package com.example.demo.controller;

import com.cloudinary.utils.ObjectUtils;
import com.example.demo.config.CloudinaryConfig;
import com.example.demo.model.Friend;
import com.example.demo.model.Profile;
import com.example.demo.model.UserPost;
import com.example.demo.model.auth.AppUser;
import com.example.demo.model.auth.AppUserRepository;
import com.example.demo.model.repositories.FriendRepository;
import com.example.demo.model.repositories.ProfileRepository;
import com.example.demo.model.repositories.UserPostRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import com.example.demo.services.NewsService;
import com.example.demo.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    AppUserRepository users;

    @Autowired
    ProfileRepository profiles;

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    WeatherService weather;

    @Autowired
    NewsService news;

    @Autowired
    UserService userService;

    @Autowired
    UserPostRepository posts;

    @Autowired
    CloudinaryConfig cloudinaryConfig;

    @RequestMapping("/w")
    public String whet(){
        return "wheathericon";
    }

    @RequestMapping("/")
    public String homepage (){
        return "homepage";
    }

    @RequestMapping("/showprofile/{id}")
    public String showProfile(@PathVariable("id") long id, Model model) {
        model.addAttribute("profile", userService.findProfile(id));
        model.addAttribute("friends", userService.getFollowees(id));
        return "profile";
    }

    @RequestMapping("/post")
    public String home(Model model) {
        model.addAttribute("newPost", new UserPost());
        model.addAttribute("posts", posts.findAllByOrderByTimePostedDesc());
        return "index";
    }

    @RequestMapping("/posting")
    public String postingPost(@ModelAttribute("newPost") UserPost post, Model model, Authentication authentication) {
        AppUser thisUser = userService.findUser(authentication);
      if (thisUser.hasProfile()) {
            post.setWhoPosted(userService.findProfile(authentication));
            post.setTimePosted(LocalDateTime.now());
            posts.save(post);
     }else{
            model.addAttribute("noProfile", true);
          model.addAttribute("profile", new Profile());
           return "profileform";
     }
        return "redirect:/post";
    }

    @RequestMapping("/headlines")
    public String headlines(Authentication authentication, Model model) {
        try {
            if (userService.findProfile(authentication).hasNoInterests()) {
                model.addAttribute("articles", news.defaultArticles());
                model.addAttribute("nointerests", true);
            } else
                model.addAttribute("articles", news.personalized(authentication));
        } catch (Exception e) {
            model.addAttribute("articles", news.defaultArticles());
        }

        return "headlines";
    }
    @RequestMapping("/searchingnews")
    public String showNews(HttpServletRequest request, Model model) {
        String terms = request.getParameter("searchnews");
        model.addAttribute("articles", news.articlesBySearch(terms));
        return "headlines";
    }

    @RequestMapping("/forecasts")
    public String weatherForecasts(Authentication authentication, Model model) {
        if (authentication != null) {
            AppUser thisUser = userService.findUser(authentication);
            if (thisUser.hasProfile()) {
                try {
                    model.addAttribute("forecasts", weather.forecasts(userService.findProfile(authentication).getZipcode()));
                    model.addAttribute("zipcode", userService.findProfile(authentication).getZipcode());
                } catch (HttpClientErrorException e) {
                    model.addAttribute("badcodeinprofile", true);
                }
            } else {
                model.addAttribute("noProfile", true);
                model.addAttribute("profile", new Profile());
                return "forecasts";
            }
        }
        return "forecasts";
    }
    @RequestMapping("/forecastswithzipcode")
    public String weatherForecastsByZipCode(HttpServletRequest request, Model model) {
        String zipcode = request.getParameter("zipcode");
        try {
            model.addAttribute("forecasts", weather.forecasts(zipcode));
            model.addAttribute("zipcode", zipcode);
        } catch (HttpClientErrorException e) {
            model.addAttribute("badzipentered", true);
        }
        return "forecasts";
    }


    @RequestMapping("/profile")
    public String showProfile(Model model, Authentication auth){
        model.addAttribute("profile", profiles.findByProfileOwnerIs(users.findByUsername(auth.getName())));
        return "profile";
    }

    @RequestMapping("/searchposts")
    public String searchForUsersFromPosts(HttpServletRequest request, Model model) {
        String term = request.getParameter("search");
        model.addAttribute("posts", posts.findAllByTextContaining(term));
        model.addAttribute("searchterm", term);
        model.addAttribute("newPost",new UserPost());
        return "profile";
    }
    @RequestMapping("/updateprofile")
    public String updateProfile(Model model, Authentication auth){
        model.addAttribute("profile", profiles.findByProfileOwnerIs(users.findByUsername(auth.getName())));
        return "updateProfile";
    }
    @PostMapping("/updateprofile")
    public String processFriend(@ModelAttribute("friend") Profile profile, Model model, MultipartHttpServletRequest request, Authentication auth) throws IOException {
        MultipartFile fi = request.getFile("file");

        AppUser user = users.findByUsername(auth.getName());
        user.setProfile(profile);
        users.save(user);
        profile.setProfileOwner(user);
        profiles.save(profile);

        if(fi.isEmpty() && profile.getProfilepicture().isEmpty()){
            return "index";
        }
        try {
            Map uploadResult = cloudinaryConfig.upload(fi.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            String myURL = (String) uploadResult.get("url");
            String uploadName = (String) uploadResult.get("public_id");
            String finalImage = cloudinaryConfig.createUrl(uploadName);
            profile.setProfilepicture(finalImage);
            profiles.save(profile);
        } catch(Exception e){
            e.printStackTrace();
            return "redirect:/updateprofile";
        }
        model.addAttribute("profile", profiles.findByProfileOwnerIs(users.findByUsername(auth.getName())));
        return "profile";
    }
    @RequestMapping("/peopleyoumayknow")
    public String userList(Model model){
        model.addAttribute("profiles", profiles.findAll());
        return "peopleToAdd";
    }

    @RequestMapping("/follow/{id}")
    public String followFriend(@PathVariable("id") long id, Model model, Authentication auth){
        Profile prfToAdd = profiles.findById(id).get();
        Friend frToAdd = new Friend();
        frToAdd.setFullName(prfToAdd.getFullName());
        frToAdd.setPicture(prfToAdd.getProfilepicture());


        AppUser user = users.findByUsername(auth.getName());
        Profile owner = profiles.findByProfileOwnerIs(user);

        owner.addFriend(frToAdd);
        owner.setProfileOwner(user);
        frToAdd.setProfile(owner);

        friendRepository.save(frToAdd);
        profiles.save(owner);
        return "redirect:/listfriends";
    }
    @RequestMapping("/listfriends")
    public String showFriends(Model model, Authentication auth){
        model.addAttribute("friends", friendRepository.findAllByProfileIs(profiles.findByProfileOwnerIs(users.findByUsername(auth.getName()))));
        return "listFriends";
    }

    @RequestMapping("/unfollow/{id}")
    public String unfollowFriend(@PathVariable("id") long id, Model model, Authentication auth){
        Friend tobeRemoved = friendRepository.findById(id).get();
        Profile owner = profiles.findByProfileOwnerIs(users.findByUsername(auth.getName()));
        owner.removeFriend(tobeRemoved);
        friendRepository.delete(tobeRemoved);
        profiles.save(owner);
        return "redirect:/listfriends";
    }

    @RequestMapping("/about")
    public String aboutProfile(Model model, Authentication auth){
        Profile prf = profiles.findByProfileOwnerIs(users.findByUsername(auth.getName()));
        model.addAttribute("aboutprofile", prf);
        return "about";
    }
}
