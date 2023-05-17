package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.iberianmotorsports.service.utils.Utils.loadContent;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(SessionController.class)
class SessionControllerTest {

    @MockBean
    SessionService sessionService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createNewSession() throws Exception {
        mockMvc.perform(post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("session.json")))
                .andExpect(status().isCreated());
    }

    @Test
    void getSessionById() throws Exception {
        mockMvc.perform(get("/session/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllSessions() throws Exception{
        mockMvc.perform(get("/session"))
                .andExpect(status().isOk());
    }

    @Test
    void updateSession() throws Exception{
        mockMvc.perform(put("/session/1").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("session.json")))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSession() throws Exception{
        mockMvc.perform(delete("/session/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("session.json")))
                .andExpect(status().isOk());
    }


}