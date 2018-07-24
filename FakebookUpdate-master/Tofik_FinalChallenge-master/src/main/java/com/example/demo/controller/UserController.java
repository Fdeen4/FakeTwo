package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.model.UserPost;
import com.example.demo.model.auth.AppUser;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String showProfile(Model model, Authentication authentication){
        AppUser thisUser = userService.findUser(authentication);
        if (thisUser.hasProfile()){
            model.addAttribute("profile", userService.findProfile(authentication));
            model.addAttribute("friends", userService.getFollowees(authentication));
        }
        else
        {
            model.addAttribute("noProfile", true);
            model.addAttribute("profile", new Profile());
            return "profileform";
        }
        return "profile";
    }
    @RequestMapping("/posting")
    public String postingPost(@ModelAttribute("newPost") UserPost post, Model model, BindingResult result, Authentication authentication, MultipartHttpServletRequest request) {
        AppUser thisUser = userService.findUser(authentication);
        MultipartFile file = request.getFile("file");
        String filter = request.getParameter("filter");
        if (thisUser.hasProfile()) {
            if (file != null && !file.isEmpty()) {
                if (!filter.equals("prompt") && !filter.isEmpty()) {
                    userService.addThePost(authentication, post, file, filter);
                }
                else
                    userService.addThePost(authentication, post, file);
            }
            else
                userService.addThePost(authentication, post);
        } else {
            model.addAttribute("noProfile", true);
            model.addAttribute("profile", new Profile());
            return "users/profileform";
        }
        return "redirect:/timeline";
    }

    @RequestMapping("/timeline") public String showUserTimeline(Model model, Authentication authentication){
        model.addAttribute("newPost", new UserPost());
        model.addAttribute("timeline", userService.getTimeline(authentication));
        return "/timeline";
    }

    @GetMapping("/form") public String getProfile(Model model, Authentication authentication){
        AppUser thisUser = userService.findUser(authentication);
        if (!thisUser.hasProfile())
            model.addAttribute("profile", new Profile());
        else
            model.addAttribute("profile", userService.findProfile(authentication));
        return "profileform";
    }
    @PostMapping("/form") public String saveProfile(@ModelAttribute("profile") Profile profile, Authentication authentication, MultipartHttpServletRequest request){
        MultipartFile file = request.getFile("file");
        if (file != null && !file.isEmpty())
            userService.saveProfile(profile, authentication/*, file*/);
        else
            userService.saveProfile(profile, authentication);
        return "profile";
    }

    @RequestMapping("/follow") public String addFriend(HttpServletRequest request, Authentication authentication, Model model){
        Long id = new Long (request.getParameter("id"));

        AppUser thisUser = userService.findUser(authentication);
        if (thisUser.hasProfile())
            userService.follow(authentication, userService.findProfile(id));
        else {
            model.addAttribute("noProfile", true);
            model.addAttribute("profile", new Profile());
            return "users/profileform";
        }
        return "redirect:/user/";
    }
    @RequestMapping("/unfollow") public String removeFriend(HttpServletRequest request, Authentication authentication, Model model) {
        Long id = new Long(request.getParameter("id"));

        AppUser thisUser = userService.findUser(authentication);
        if (thisUser.hasProfile())
            userService.unFollow(authentication, userService.findProfile(id));
        else {
            model.addAttribute("noProfile", true);
            model.addAttribute("profile", new Profile());
            return "users/profileform";
        }
        return "redirect:/user/";
    }

//
//    @GetMapping("/form")
//    public String getProfile(Model model, Authentication authentication){
//        AppUser thisUser = userService.findUser(authentication);
////        if (!thisUser.hasProfile())
////            model.addAttribute("profile", new Profile());
////        else
//            model.addAttribute("profile", userService.findProfile(authentication));
//        return "profileform";
//    }
//    @PostMapping("/form")
//    public String saveProfile(@ModelAttribute("profile") Profile profile, Authentication authentication){
//        userService.saveProfile(profile, authentication);
//        return "redirect:/user/";
//    }
}