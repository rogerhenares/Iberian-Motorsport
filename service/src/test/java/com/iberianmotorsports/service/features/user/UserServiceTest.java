package com.iberianmotorsports.service.features.user;

import com.iberianmotorsports.UserFactory;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.repository.UserRepository;
import com.iberianmotorsports.service.service.UserService;
import com.iberianmotorsports.service.service.implementation.UserServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.iberianmotorsports.service.utils.Utils.loadContentString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    private UserService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    RestTemplate restTemplate;

    @Value("${steam.client.id}")
    private String apiKey;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @BeforeEach
    public void init() {
        service = new UserServiceImpl(userRepository, restTemplate, apiKey);
    }

    @Nested
    class saveUser{
        @Test
        public void saveUser() {
            givenUserIdRetrieveUserData();
            givenUserRepositorySave();

            service.saveUser(Long.MAX_VALUE);

            verify(userRepository).save(userCaptor.capture());
            assertEquals(UserFactory.userFromSteam(), userCaptor.getValue());
        }

        @Test
        public void saveUserWhenWrongSteamId(){
            givenUserIdRetrieveUserDataFailed();

            Exception exception = assertThrows(ServiceException.class,
                    ()-> service.saveUser(Long.MAX_VALUE));

            verify(userRepository, times(0)).save(any());
            Assertions.assertEquals(ErrorMessages.STEAM_DATA.getDescription(), exception.getMessage());
        }

        @Test
        public void saveWhenUserAlreadyExists(){
            givenUserAlreadyExists();

            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> service.saveUser(Long.MAX_VALUE));

            verify(userRepository, times(0)).save(any());
            Assertions.assertEquals(ErrorMessages.DUPLICATE_USER.getDescription(), exception.getMessage());
        }
    }

    @Nested
    class updateUser{

        @Test
        public void updateUser() {
            User testUser = UserFactory.updatedUser();

            User updatedUser = service.updateUser(testUser);

            verify(userRepository).save(userCaptor.capture());
            assertNotEquals(userCaptor.getValue(), UserFactory.user());
        }

        @Test
        public void updateUserWithInvalidFormat() {
            User testUser = UserFactory.userInvalidFormat();

            assertThrows(ServiceException.class,
                    ()-> service.updateUser(testUser));

            verify(userRepository, times(0)).save(any());
        }

    }

    @Nested
    class deleteUser{

        @Test
        public void deleteUser() {
            givenUserAlreadyExists();

            service.deleteUser(anyLong());

            verify(userRepository).deleteById(anyLong());
        }
    }


    @Nested
    class findUser {

        @Test
        public void findUserById() {

            when(userRepository.findById(anyLong())).thenReturn(Optional.of(UserFactory.user()));

            userRepository.findById(anyLong());

            verify(userRepository).findById(anyLong());

        }

        @Test
        public void findUserWithInvalidId() {

            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(ServiceException.class, () ->service.findUserBySteamId(anyLong()));

            verify(userRepository).findById(anyLong());
            Assertions.assertEquals(ErrorMessages.USER_NOT_IN_DB.getDescription(), exception.getMessage());

        }

        @Test
        public void findUserByName() {
            when(userRepository.findByFirstName(anyString())).thenReturn(Optional.of(UserFactory.user()));

            userRepository.findByFirstName(anyString());

            verify(userRepository).findByFirstName(anyString());
        }

        @Test
        public void findUserInvalidName() {
            when(userRepository.findByFirstName(anyString())).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(ServiceException.class, () -> service.findUserByName(anyString()));

            verify(userRepository).findByFirstName(anyString());
            Assertions.assertEquals(ErrorMessages.USER_NOT_IN_DB.getDescription(), exception.getMessage());
        }

        @Test
        public void findAllUsers() {
            when(userRepository.findAll()).thenReturn(List.of(UserFactory.user()));

            userRepository.findAll();

            verify(userRepository).findAll();
        }

    }

    private void givenUserRepositorySave() {
        when(userRepository.save(any(User.class))).then((Answer<User>) invocation -> {
            User user = invocation.getArgument(0);
            user.setUserId(1L);
            return user;
        });
    }
    private void givenUserIdRetrieveUserData() {
        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenReturn(loadContentString("userSteamReturn.json"));
    }

    private void givenUserIdRetrieveUserDataFailed() {
        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenReturn(loadContentString("userSteamReturnEmpty.json"));
    }

    private void givenUserAlreadyExists() {
        when(userRepository.findBySteamId(anyLong())).thenReturn(Optional.of(UserFactory.user()));
    }


    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    public void saveUserThrowsExceptionSteamIdInvalid(User user, Object expected){

    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(UserFactory.user(), true),
                Arguments.of(UserFactory.userInvalidFormat(), true),
                Arguments.of(UserFactory.updatedUser(), true),
                Arguments.of(UserFactory.userWrongSteamId(), true)
        );
    }

}
