package com.iberianmotorsports.service.features.user;

import com.iberianmotorsports.UserFactory;
import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:it.properties")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setupDataBase() {
        User userDummy = UserFactory.user();
        userDummy.setUserId(null);
        User savedUser = userRepository.save(userDummy);
        assertNotNull(savedUser);
    }


    @Test
    void saveUserFirstNameNull() {
        User userDummy = UserFactory.user();
        userDummy.setFirstName(null);
        assertThrows(ConstraintViolationException.class, () -> userRepository.save(userDummy));
    }

    @Test
    void fetchUserBySteamId() {
        User userDummy = UserFactory.user();
        Long steamId = userDummy.getSteamId();

        Optional<User> foundUser = userRepository.findBySteamId(steamId);
        Assertions.assertThat(foundUser.isPresent());
        Assertions.assertThat(foundUser.get()).isEqualToIgnoringGivenFields(userDummy, "userId");
    }

    @Test
    void fetchUserByFirstName() {
        User userDummy = UserFactory.user();
        String name = userDummy.getFirstName();

        Optional<User> foundUser = userRepository.findByFirstName(name);
        Assertions.assertThat(foundUser.isPresent());
        Assertions.assertThat(foundUser.get()).isEqualToIgnoringGivenFields(userDummy, "userId");
    }

    @Test
    void fetchAllUser() {
        List<User> userList = userRepository.findAll();
        assertFalse(userList.isEmpty());
    }

    @Test
    void deleteUser() {
        User userDummy = (User) userRepository.findAll().get(0);
        userRepository.delete(userDummy);

        assertTrue(userRepository.findBySteamId(userDummy.getSteamId()).isEmpty());
    }

}
