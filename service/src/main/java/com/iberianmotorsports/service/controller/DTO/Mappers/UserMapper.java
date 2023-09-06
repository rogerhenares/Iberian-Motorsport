package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.UserDTO;
import com.iberianmotorsports.service.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserMapper implements Function<UserDTO, User> {


    public UserMapper() {
    }


    @Override
    public User apply(UserDTO userDTO) {
        User user = new User();
        user.setSteamId(Long.valueOf(userDTO.steamId()));
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setShortName(userDTO.shortName());
        user.setNationality(userDTO.nationality());
        user.setRolesToAdd(userDTO.roleList().stream().toList());
        return user;
    }
}
