package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.RaceRulesFactory;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceRulesDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceRulesMapper;
import com.iberianmotorsports.service.service.AuthService;
import com.iberianmotorsports.service.service.RaceRulesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.iberianmotorsports.service.utils.Utils.loadContent;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(RaceRulesController.class)
class RaceRulesControllerTest {

    @MockBean
    RaceRulesService raceRulesService;

    @MockBean
    AuthService authService;

    @MockBean
    RaceRulesDTOMapper raceRulesDTOMapper;

    @MockBean
    RaceRulesMapper raceRulesMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createNewRaceRules() throws Exception {
        mockMvc.perform(post("/race-rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("raceRules.json")))
                .andExpect(status().isCreated());
    }

    @Test
    void getRaceRulesById() throws Exception {
        mockMvc.perform(get("/race-rules/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllRaceRuless() throws Exception{
        mockMvc.perform(get("/race-rules"))
                .andExpect(status().isOk());
    }

    @Test
    void updateRaceRules() throws Exception{
        when(raceRulesMapper.apply(ArgumentMatchers.any())).thenReturn(RaceRulesFactory.raceRules());

        mockMvc.perform(put("/race-rules/1").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("raceRules.json")))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRaceRules() throws Exception{
        mockMvc.perform(delete("/race-rules/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("raceRules.json")))
                .andExpect(status().isOk());
    }


}
