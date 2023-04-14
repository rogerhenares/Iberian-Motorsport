package com.iberianmotorsports.service.features.user;

import com.iberianmotorsports.UserFactory;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.repository.OpenIdRepository;
import com.iberianmotorsports.service.repository.UserRepository;
import com.iberianmotorsports.service.service.UserService;
import com.iberianmotorsports.service.service.implementation.UserServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    private UserService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    OpenIdRepository openIdRepository;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @BeforeEach
    public void init() {
        service = new UserServiceImpl(userRepository, openIdRepository);
    }

    @Nested
    class saveUser{
        @Test
        public void saveUser() {

            givenUserRepositorySave();
            User testUser = UserFactory.user();

            User savedUser = service.saveUser(testUser);

            verify(userRepository).save(userCaptor.capture());
            assertEquals(UserFactory.user(), userCaptor.getValue());


        }

        @Test
        public void saveUserWhenPlayerIdIsNotDefined(){
            User testUser = UserFactory.user();
            testUser.setPlayerId(null);


            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> service.saveUser(testUser));

            verify(userRepository, times(0)).save(any());
            Assertions.assertEquals(ErrorMessages.STEAM_ID_UNDEFINED.getDescription(), exception.getMessage());

        }
        @Test
        public void saveUserWhenPlayerIdAlreadyExists(){
            User testUser = UserFactory.user();
            givenUserAlreadyExists();

            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> service.saveUser(testUser));

            verify(userRepository, times(0)).save(any());
            Assertions.assertEquals(ErrorMessages.DUPLICATE_USER.getDescription(), exception.getMessage());

        }

        @Test
        public void saveUserWithInvalidFormat(){
            User testUser = UserFactory.userInvalidFormat();

            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> service.saveUser(testUser));

            verify(userRepository, times(0)).save(any());
            Assertions.assertEquals(ErrorMessages.FIRST_NAME.getDescription(), exception.getMessage());
        }


    }

    @Nested
    class updateUser{

        @Test
        public void updateUser() {
            givenUserRepositorySave();
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

            RuntimeException exception = assertThrows(ServiceException.class, () ->service.findUserById(anyLong()));

            verify(userRepository).findById(anyLong());
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

    private void givenUserAlreadyExists() {
        when(userRepository.findByPlayerId(anyLong())).thenReturn(Optional.of(UserFactory.user()));
    }


}
