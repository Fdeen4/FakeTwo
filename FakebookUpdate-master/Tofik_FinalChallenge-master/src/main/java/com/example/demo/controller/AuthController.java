package com.example.demo.controller;

import com.example.demo.model.Profile;
import com.example.demo.model.auth.AppUser;
import com.example.demo.model.auth.AppUserRepository;
import com.example.demo.model.auth.UserRoleRepository;
import com.example.demo.model.repositories.ProfileRepository;
import com.example.demo.model.repositories.UserPostRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Controller
public class AuthController {
    @Autowired
    UserPostRepository posts;

    @Autowired
    UserService userService;

    @Autowired
    AppUserRepository users;

    @Autowired
    UserRoleRepository roles;

    @Autowired
    ProfileRepository profileRepository;

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String regiter(Model model) {
        model.addAttribute("newUser", new AppUser());
        return "register";
    }

/*    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("newUser") AppUser user, Model model) {

        if(users.existsByUsername(user.getUsername())){
            model.addAttribute("usernameErr", users.existsByUsername(user.getUsername()));
            return "register";
        }
        Profile profile = new Profile();
        user.addRole(roles.findByRole("USER"));
        users.save(user);
        //user.setProfile(profile);
        profile.setProfileOwner(user);
        profile.setUsername(user.getUsername());
        profileRepository.save(profile);
        return "redirect:/profile";
    }*/
@PostMapping("/register")
public String saveUser(@Valid @ModelAttribute("newUser") AppUser user, Model model,
                       BindingResult bindingResult, HttpServletRequest request) {

    if(users.existsByUsername(user.getUsername())){
        model.addAttribute("usernameErr", users.existsByUsername(user.getUsername()));
        return "register";
    }

    if(bindingResult.hasErrors()){
        return "register";
    } else {
        user.setEnabled(true);
        user.setConfirmationcode(UUID.randomUUID().toString());

        Profile profile = new Profile();
        user.addRole(roles.findByRole("USER"));
        users.save(user);
        profile.setProfileOwner(user);
        profile.setUsername(user.getUsername());
        profileRepository.save(profile);

        String url = request.getScheme() + "://" + request.getServerName() + ":8080";

        SimpleMailMessage toSend = new SimpleMailMessage();
        toSend.setTo(user.getEmail());
        toSend.setSubject("Activate your account");
        toSend.setText("Please click the link to activate your account:" +
                url + "/confirm?token=" + user.getConfirmationcode());
        toSend.setFrom("tofgisfad1@gmail.com");
        userService.sendEmail(toSend);
    }
    return "redirect:/";
}
    @RequestMapping("/confirm")
    public String confirmEmail(Model model, @RequestParam("token") String token){
        AppUser user = userService.findByConfirmationco(token);
        if(user == null){
            model.addAttribute("invalid", "Enter valid details");
        } else {
            model.addAttribute("newUser",user);
        }
        return "confirm";
    }

    @PostMapping("/confirm")
    public String processconfirm(Model model, @RequestParam Map requestParams){
        AppUser user = userService.findByConfirmationco((String)requestParams.get("token"));
        user.setPassword((String) requestParams.get("password"));

        user.setEnabled(true);
        userService.saveMe(user);
        model.addAttribute("success", "Password set");
        return "redirect:/login";
    }
}
