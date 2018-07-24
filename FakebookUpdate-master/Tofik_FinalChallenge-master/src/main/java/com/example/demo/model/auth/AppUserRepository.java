package com.example.demo.model.auth;

import com.example.demo.model.auth.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    boolean existsByUsername(String username);
    AppUser findByEmail(String email);
    AppUser findByConfirmationcode(String confirmationcode);
}
