package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.model.MessageResponse;
import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<?> createNewUser(Long steamId) throws Exception {
        User createdUser = userService.saveUser(steamId);
        return new ResponseEntity<Object>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long userId) throws ServiceException {
        User user = userService.findUserBySteamId(userId);
        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() throws ServiceException{
        Page<User> userList = userService.findAllUsers();
        return new ResponseEntity<Object>(userList, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long userId, @RequestBody User user) throws ServiceException{
        user.setUserId(userId);
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<Object>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long steamId) throws ServiceException{
        userService.deleteUser(steamId);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("User has been deleted successfully.");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }

}
