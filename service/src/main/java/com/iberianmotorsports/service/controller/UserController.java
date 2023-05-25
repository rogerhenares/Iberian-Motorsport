package com.iberianmotorsports.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iberianmotorsports.service.ErrorMessages;
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
import org.springframework.web.client.RestTemplate;


@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private RestTemplate restTemplate = new RestTemplate();

    public User getPlayerSummary(String steamId) throws Exception {
        String apiKey = "6614BF6FC7820DF1DD2C875DB66C5D82";
        String url = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/" + "?key=" + apiKey + "&steamids=" + steamId;

        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(response, User.class);

        return user;
    }

    @PostMapping
    public ResponseEntity<?> createNewUser(String steamId) throws Exception {
        User user = getPlayerSummary(steamId);
        User createdUser = userService.saveUser(user);
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
        if(!userService.isAlreadyInDatabase(steamId)) {
            throw new ServiceException(ErrorMessages.USER_NOT_IN_DB.getDescription());
        }
        userService.deleteUser(steamId);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("User has been deleted successfully.");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }



}
