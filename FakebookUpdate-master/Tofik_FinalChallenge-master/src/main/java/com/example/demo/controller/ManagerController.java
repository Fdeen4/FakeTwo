package com.example.demo.controller;
import com.example.demo.model.auth.AppUser;
import com.example.demo.model.auth.AppUserRepository;
import com.example.demo.model.auth.UserRoleRepository;
import com.example.demo.model.repositories.UserPostRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

@Controller
@RequestMapping("/manage")
public class ManagerController {
    @Autowired
    AppUserRepository users;
    @Autowired
    UserRoleRepository roles;
    @Autowired
    UserPostRepository posts;

    @RequestMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", users.findAll());
        return "userslist";
    }

    @RequestMapping("/banuser/{id}")
    public String banning(@PathVariable("id") long id){
        AppUser user = users.findById(id).get();
        user.setRoles(new HashSet<>());
        user.addRole(roles.findByRole("SUSPENDED"));
        users.save(user);
        return "redirect:/manage/users";
    }
    @RequestMapping("/deletepost") public String deletePost(HttpServletRequest request){
        long id = new Long(request.getParameter("id"));
        posts.deleteById(id);
        return "redirect:/profile";
    }
}