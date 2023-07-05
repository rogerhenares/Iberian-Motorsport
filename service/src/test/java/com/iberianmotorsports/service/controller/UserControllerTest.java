package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.UserFactory;
import com.iberianmotorsports.service.controller.DTO.Mappers.UserDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.UserMapper;
import com.iberianmotorsports.service.service.AuthService;
import com.iberianmotorsports.service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.iberianmotorsports.service.utils.Utils.loadContent;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    UserService userService;

    @MockBean
    AuthService authService;

    @MockBean
    UserDTOMapper userDTOMapper;

    @MockBean
    UserMapper userMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createNewUser() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("userSteamReturn.json")))
                .andExpect(status().isCreated());
    }

    @Test
    void getUserById() throws Exception {
        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllUsers() throws Exception{
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception{
        mockMvc.perform(put("/user").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("userSteamReturn.json")))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception{
        mockMvc.perform(delete("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("userSteamReturn.json")))
                .andExpect(status().isOk());
    }


}