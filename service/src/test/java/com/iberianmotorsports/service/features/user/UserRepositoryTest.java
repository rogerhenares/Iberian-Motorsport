package com.iberianmotorsports.service.features.user;

import com.iberianmotorsports.UserFactory;
import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

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
        userRepository.save(userDummy);
    }

    @Test
    void fetchAllUser() {
        List<User> userList = userRepository.findAll();
        assertFalse(userList.isEmpty());
    }
}
