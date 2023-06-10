package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.repository.RoleRepository;
import com.iberianmotorsports.service.repository.UserRepository;
import com.iberianmotorsports.service.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final String BASIC_USER = "BASIC_USER";
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RestTemplate restTemplate;
    private Environment env;

    @Override
    public User saveUser(Long steamId) {
        if (isAlreadyInDatabase(steamId)) throw new ServiceException(ErrorMessages.DUPLICATE_USER.getDescription());
        User user = getPlayerSummary(String.valueOf(steamId));
        user.setRoles(List.of(roleRepository.findRoleByRole(BASIC_USER)));
        User userSaved = userRepository.save(user);
        return userSaved;
    }

    @Override
    public User getPlayerSummary(String steamId) {

        String apiKey = env.getProperty("steam.client.id");
        String url = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/" + "?key=" + apiKey + "&steamids=" + steamId;

        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        User user;
        try {
            JsonNode rootNode = mapper.readTree(response);
            JsonNode playerNode = rootNode.path("response").path("players").get(0);
            user = mapper.readValue(playerNode.toString(), User.class);
        } catch (JsonProcessingException e) {
            throw new ServiceException(ErrorMessages.STEAM_DATA.getDescription());
        }
         catch (NullPointerException e) {
             throw new ServiceException(ErrorMessages.STEAM_DATA.getDescription());
         }

        return user;
    }

    @Override
    public User findUserBySteamId(Long steamId) {
        return userRepository.findBySteamId(steamId).orElseThrow(() ->
                new ServiceException(ErrorMessages.USER_NOT_IN_DB.getDescription()));
    }

    @Override
    public User findUserByName(String name) {
        Optional<User> userOptional = userRepository.findByFirstName(name);
        if(userOptional.isEmpty()) throw new ServiceException(ErrorMessages.USER_NOT_IN_DB.getDescription());
        return userOptional.orElse(null);
    }

    @Override
    public Page<User> findAllUsers(Pageable pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    @Override
    public User updateUser(User user) {
        if(user.getLastName() == null) throw new ServiceException(ErrorMessages.LAST_NAME.getDescription());
        if(user.getShortName() == null) throw new ServiceException(ErrorMessages.SHORT_NAME.getDescription());
        if(user.getNationality() == null) throw new ServiceException(ErrorMessages.NATIONALITY.getDescription());
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
        Optional<User> userOptional = userRepository.findBySteamId(steamId);
        return userOptional.isPresent();
    }

}
