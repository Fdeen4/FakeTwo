package com.example.demo.config;

import com.example.demo.model.auth.AppUserRepository;
import com.example.demo.service.SSUDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AppUserRepository userRepo;

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUDS(userRepo);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN", "MANAGER")
                .antMatchers("/manage/**").hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/h2/**").hasAnyAuthority("ADMIN")
                .antMatchers("/**").permitAll()

                .anyRequest().authenticated()

                .and().formLogin().loginPage("/login")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}