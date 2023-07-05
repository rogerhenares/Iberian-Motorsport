package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.RaceFactory;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceMapper;
import com.iberianmotorsports.service.service.AuthService;
import com.iberianmotorsports.service.service.RaceService;
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
@WebMvcTest(RaceController.class)
class RaceControllerTest {

    @MockBean
    RaceService raceService;

    @MockBean
    AuthService authService;

    @MockBean
    RaceDTOMapper raceDTOMapper;

    @MockBean
    RaceMapper raceMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createNewRace() throws Exception {
        mockMvc.perform(post("/race")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("race.json")))
                .andExpect(status().isCreated());
    }

    @Test
    void getRaceById() throws Exception {
        mockMvc.perform(get("/race/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getRaceByName() throws Exception {
        mockMvc.perform(get("/race/name/testRace"))
                .andExpect(status().isOk());

    }

    @Test
    void getAllRaces() throws Exception{
        mockMvc.perform(get("/race"))
                .andExpect(status().isOk());
    }

    @Test
    void updateRace() throws Exception{
        when(raceMapper.apply(ArgumentMatchers.any())).thenReturn(RaceFactory.race());

        mockMvc.perform(put("/race/1").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("race.json")))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRace() throws Exception{
        mockMvc.perform(delete("/race/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loadContent("race.json")))
                .andExpect(status().isOk());
    }


}
