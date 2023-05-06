package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.repository.UserRepository;
import com.iberianmotorsports.service.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.iberianmotorsports.service.Utils.Utils.defaultPageable;


@AllArgsConstructor
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    private RestTemplate restTemplate;


    @Override
    public User saveUser(Long steamId) {
        if (isAlreadyInDatabase(steamId)) throw new ServiceException(ErrorMessages.DUPLICATE_USER.getDescription());
        User user = getPlayerSummary(String.valueOf(steamId));
        return userRepository.save(user);
    }

    //TODO get the apikey from properties
    @Override
    public User getPlayerSummary(String steamId) {
        String apiKey = "6614BF6FC7820DF1DD2C875DB66C5D82";
        String url = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/" + "?key=" + apiKey + "&steamids=" + steamId;

        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        User user;
        try {
            JsonNode rootNode = mapper.readTree(response);
            JsonNode playerNode = rootNode.path("response").path("players").get(0);
            user = mapper.readValue(playerNode.toString(), User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public User findUserBySteamId(Long steamId) {
        Optional<User> userOptional = userRepository.findById(steamId);
        if(userOptional.isEmpty()) throw new ServiceException(ErrorMessages.USER_NOT_IN_DB.getDescription());
        return userOptional.orElse(null);
    }

    @Override
    public Page<User> findAllUsers() {
        return userRepository.findAll(defaultPageable);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long steamId) {
        if(!isAlreadyInDatabase(steamId)) {
            throw new ServiceException(ErrorMessages.USER_NOT_IN_DB.getDescription());
        }
        userRepository.deleteById(steamId);
    }

    public Boolean isAlreadyInDatabase(Long steamId) {
        Optional<User> userOptional = userRepository.findById(steamId);
        return userOptional.isPresent();
    }

}