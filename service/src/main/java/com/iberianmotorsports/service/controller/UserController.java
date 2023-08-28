package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.controller.DTO.Mappers.UserDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.UserMapper;
import com.iberianmotorsports.service.controller.DTO.UserDTO;
import com.iberianmotorsports.service.controller.DTO.MessageResponse;
import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDTOMapper userDTOMapper;
    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public ResponseEntity<?> createNewUser(Long steamId) {
        User createdUser = userService.saveUser(steamId);
        UserDTO createdUserDTO = userDTOMapper.apply(createdUser);
        return new ResponseEntity<Object>(createdUserDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{steamId}")
    public ResponseEntity<?> getUserBySteamId(@PathVariable("steamId") Long steamId) throws ServiceException {
        User user = userService.findUserBySteamId(steamId);
        UserDTO userDTO = userDTOMapper.apply(user);
        return new ResponseEntity<Object>(userDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable("name") String name) throws ServiceException {
        User user = userService.findUserByName(name);
        UserDTO userDTO = userDTOMapper.apply(user);
        return new ResponseEntity<Object>(userDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/loggedUser")
    public ResponseEntity<?> getLoggedUser() throws ServiceException {
        Long steamId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserBySteamId(steamId);
        UserDTO userDTO = userDTOMapper.apply(user);
        return new ResponseEntity<Object>(userDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/admin")
    public ResponseEntity<?> getAllUsers(Pageable pageRequest) throws ServiceException {
        Page<UserDTO> userList = userService.findAllUsers(pageRequest).map(userDTOMapper);
        return new ResponseEntity<Object>(userList, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) throws ServiceException{
        Long steamId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userToUpdate = userMapper.apply(userDTO);
        userToUpdate.setSteamId(steamId);
        User updatedUser = userService.updateUser(userToUpdate);
        UserDTO updatedUserDTO = userDTOMapper.apply(updatedUser);
        return new ResponseEntity<Object>(updatedUserDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/admin/{steamId}")
    public ResponseEntity<?> deleteUser(@PathVariable("steamId") Long steamId) throws ServiceException{
        userService.deleteUser(steamId);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("User has been deleted successfully.");
        return new ResponseEntity<Object>(messageResponse, HttpStatus.OK);
    }

}
