package com.example.demo.service;

import com.example.demo.model.auth.AppUser;
import com.example.demo.model.auth.AppUserRepository;
import com.example.demo.model.auth.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SSUDS implements UserDetailsService {
    private AppUserRepository repo;

    public SSUDS(AppUserRepository userRepo) {
        this.repo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppUser theUser = repo.findByUsername(s);
        if(theUser==null)
            throw new UsernameNotFoundException("Unable to find that user!!!!");
        return new User(theUser.getUsername(),theUser.getPassword(),myAuthorities(theUser));
    }

    private Collection<? extends GrantedAuthority> myAuthorities(AppUser theUser) {
        Set<GrantedAuthority> userAuthorities = new HashSet<>();
        for (UserRole role:theUser.getRoles()){
            userAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return userAuthorities;
    }
}