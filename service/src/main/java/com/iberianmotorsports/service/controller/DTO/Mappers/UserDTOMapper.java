package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.UserDTO;
import com.iberianmotorsports.service.model.Role;
import com.iberianmotorsports.service.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getUserId(),
                String.valueOf(user.getSteamId()),
                user.getFirstName(),
                user.getShortName(),
                user.getLastName(),
                user.getNationality(),
                user.getRoles().stream().map(Role::getRole).toList()
        );
    }
}
