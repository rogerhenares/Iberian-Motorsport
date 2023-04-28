package com.iberianmotorsports.service.service;


import com.iberianmotorsports.service.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User saveUser(User user);

    User findUserBySteamId(Long steamId);

    Page<User> findAllUsers();

    User updateUser(User user);

    void deleteUser(Long steamId);

    Boolean isAlreadyInDatabase(Long steamId);

}
