package com.iberianmotorsports.service.service;


import com.iberianmotorsports.service.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User saveUser(User user);

    User findUserById(Long userId);

    Page<User> findAllUsers();

    User updateUser(User user);

    void deleteUser(Long userId);

    Boolean isAlreadyInDatabase(Long userId);

    Boolean playerIdIsAlreadyInDatabase(Long playerId);

}
