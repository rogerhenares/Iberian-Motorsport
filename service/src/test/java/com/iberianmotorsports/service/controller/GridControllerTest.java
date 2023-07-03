package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.controller.DTO.Mappers.GridDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.GridMapper;
import com.iberianmotorsports.service.service.AuthService;
import com.iberianmotorsports.service.service.GridService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(GridController.class)
public class GridControllerTest {

    @MockBean
    GridService gridService;

    @MockBean
    AuthService authService;

    @MockBean
    GridDTOMapper gridDTOMapper;

    @MockBean
    GridMapper gridMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() { mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();}

    @Test
    public void getGridForChampionship() throws Exception {
        mockMvc.perform(get("/grid/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void createNewGrid() throws Exception {
        mockMvc.perform(post("/grid")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(loadContent("grid.json")))
                .andExpect(status().isCreated());
    }

    @Test
    public void addDriver() throws Exception {
        mockMvc.perform(put("/grid/add/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(loadContent("grid.json")))
                .andExpect(status().isOk());
    }

    @Test
    public void removeDriver() throws Exception {
        mockMvc.perform(delete("/grid/remove/1")
                    .param("steamId", "2"))
                .andExpect(status().isOk());
    }

}
