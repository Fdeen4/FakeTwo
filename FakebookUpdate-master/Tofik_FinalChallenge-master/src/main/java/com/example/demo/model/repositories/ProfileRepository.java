package com.example.demo.model.repositories;

import com.example.demo.model.Profile;
import com.example.demo.model.auth.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
    Profile findByProfileOwnerIs(AppUser user);
    Profile findByProfileOwner_Username(String username);
}
