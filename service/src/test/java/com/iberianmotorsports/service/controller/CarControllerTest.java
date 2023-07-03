package com.iberianmotorsports.service.controller;

import com.iberianmotorsports.service.controller.DTO.Mappers.CarDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.CarMapper;
import com.iberianmotorsports.service.service.AuthService;
import com.iberianmotorsports.service.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @MockBean
    CarService carService;

    @MockBean
    AuthService authService;

    @MockBean
    CarDTOMapper carDTOMapper;

    @MockBean
    CarMapper carMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup(){ mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();}

    @Test
    public void getCarByCategory() throws Exception {
        mockMvc.perform(get("/car/category/GT3"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCarByModel() throws Exception {
        mockMvc.perform(get("/car/model/porsche"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCarById() throws Exception {
        mockMvc.perform(get("/car/id/1"))
                .andExpect(status().isOk());
    }


}
