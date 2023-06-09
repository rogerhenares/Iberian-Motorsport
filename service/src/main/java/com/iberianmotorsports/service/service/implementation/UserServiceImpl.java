package com.iberianmotorsports.service.service.implementation;

import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.repository.OpenIdRepository;
import com.iberianmotorsports.service.repository.UserRepository;
import com.iberianmotorsports.service.service.UserService;
import com.iberianmotorsports.service.ErrorMessages;

import java.lang.Throwable;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.rmi.ServerException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.core.io.NumberInput.parseLong;


@AllArgsConstructor
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    OpenIdRepository openIdRepository;

    static final Pageable pageable = PageRequest.of(0,10);


    @Override
    public User saveUser(User user) {
        user = findSteamInfo(user);
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) throw new ServiceException(ErrorMessages.USER_NOT_IN_DB.getDescription());
        return userOptional.orElse(null);
    }

    @Override
    public Page<User> findAllUsers() {
        return userRepository.findAll(pageable);
    }

    @Override
    public User updateUser(User user) {
        if(user.getFirstName() == null || user.getFirstName().isBlank()) throw new ServiceException(ErrorMessages.FIRST_NAME.getDescription());
        if(user.getLastName() == null || user.getLastName().isBlank()) throw new ServiceException(ErrorMessages.LAST_NAME.getDescription());
        if(user.getShortName() == null || user.getLastName().isBlank()) throw new ServiceException(ErrorMessages.SHORT_NAME.getDescription());
        if(user.getNationality() == null || user.getNationality().isBlank()) throw new ServiceException(ErrorMessages.NATIONALITY.getDescription());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);

    }

    public Boolean isAlreadyInDatabase(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.isPresent();
    }

    public Boolean playerIdIsAlreadyInDatabase(Long playerId) {
        Optional<User> userOptional = userRepository.findByPlayerId(playerId);
        return userOptional.isPresent();
    }

    private List<String> getSteamUserInfo(User user) {
        List<String> userInfo = new ArrayList<>();
        String openIdUrl = getOpenId(user);
        String userURI = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=657C2B6E0B671BB9980A7256B162BFFE&steamids=" + openIdUrl;
        return userInfo;
    }

     private String getOpenId(User user) {
        String openIdUrl = openIdRepository.findByUser(user).getOpenIdUrl();
        return openIdUrl.substring(36);
     }

     private User findSteamInfo(User user) {
        List<String> userInfo = getSteamUserInfo(user);
        user.setPlayerId(parseLong(userInfo.get(0)));
        user.setFirstName(userInfo.get(3));
        return user;
     }

}