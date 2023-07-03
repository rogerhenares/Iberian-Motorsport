package com.iberianmotorsports;

import com.iberianmotorsports.service.model.Role;
import com.iberianmotorsports.service.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserFactory{

    public static final Long userId = 1L;

    public static final Long steamId = 99999999999999999L;

    public static final String firstName = "TestFirstName";

    public static final String lastName = "TestLastName";

    public static final String shortName = "TestShortName";

    public static final String nationality = "TestNationality";

    public static final List<Role> roles = new ArrayList<>();

    public static User user(){
        User user = new User();
        user.setUserId(userId);
        user.setSteamId(steamId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setShortName(shortName);
        user.setNationality(nationality);
        user.setRoles(roles);
        return user;
    }

    public static User userInvalidFormat() {
        User user = new User();
        user.setUserId(userId);
        user.setSteamId(steamId);
        user.setFirstName("");
        user.setLastName("");
        return user;
    }

    public static User userWrongSteamId() {
        User user = new User();
        user.setSteamId(45L);
        return user;
    }

    public static User updatedUser() {
        User user = new User();
        user.setFirstName("Antonio");
        user.setLastName("Atapuerca");
        user.setShortName("Anta");
        user.setNationality("Spa");
        return user;
    }

    public static User userFromSteam(){
        User user = user();
        user.setShortName(null);
        user.setLastName(null);
        return user;
    }

    public static List<User> userList(Integer numberOfUsers) {
        List<User> userList = new ArrayList<>();
        for(int i = 0; i < numberOfUsers; i++) {
            User user = UserFactory.user();
            userList.add(user);
        }
        return userList;
    }

}
