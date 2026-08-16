package com.example.CodeReviewApp.Repo;

import java.util.List;
import java.util.Optional;

import com.example.CodeReviewApp.Models.User;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    boolean checkExistsByEmail(String email);

    void insertUser(User user);

    boolean usersExist(List<Long> userIds);

    // this is for wbesocket connection
    String getUserEmailById(Long id);

}