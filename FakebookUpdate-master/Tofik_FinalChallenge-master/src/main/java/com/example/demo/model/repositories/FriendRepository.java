package com.example.demo.model.repositories;

import com.example.demo.model.Friend;
import com.example.demo.model.Profile;
import org.springframework.data.repository.CrudRepository;

public interface FriendRepository extends CrudRepository<Friend, Long> {
    Iterable <Friend> findAllByProfileIs(Profile profile);
}
