package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.service.ChampionshipService;
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
@WebMvcTest(ChampionshipController.class)
class ChampionshipControllerTest {

    @MockBean
    ChampionshipService championshipService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createNewChampionship() throws Exception {
        mockMvc.perform(post("/championship")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("championship.json")))
                .andExpect(status().isCreated());
    }

    @Test
    void getChampionshipById() throws Exception {
        mockMvc.perform(get("/championship/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getChampionshipByName() throws Exception {
        mockMvc.perform(get("/championship/name/testChampionship"))
                .andExpect(status().isOk());

    }

    @Test
    void getAllChampionships() throws Exception{
        mockMvc.perform(get("/championship"))
                .andExpect(status().isOk());
    }

    @Test
    void updateChampionship() throws Exception{
        mockMvc.perform(put("/championship/1").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("championship.json")))
                .andExpect(status().isOk());
    }

    @Test
    void deleteChampionship() throws Exception{
        mockMvc.perform(delete("/championship/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("championship.json")))
                .andExpect(status().isOk());
    }


}
