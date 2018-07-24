package com.example.demo.model.repositories;
import com.example.demo.model.Follow;
import com.example.demo.model.Profile;
import org.springframework.data.repository.CrudRepository;

public interface FollowRepository extends CrudRepository<Follow, Long> {
    Iterable<Follow> findAllByFollower_ProfileOwner_Username(String username);
    Iterable<Follow> findAllByFollower_Id(long id);
    Follow findByFollower_ProfileOwner_UsernameAndFollowee(String followerUsername, Profile followeeProfile);
    boolean existsByFollowerAndFollowee(Profile followerProfile, Profile followeeProfile);

    Follow findByFollower_ProfileOwner_UsernameAndFollowee_ProfileOwner_Username(String followerUsername, String followeeUsername);
    Follow findByFollower_ProfileOwner_IdAndFollowee_ProfileOwner_Id(long followerId, long followeeId);
}