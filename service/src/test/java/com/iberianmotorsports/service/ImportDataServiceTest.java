package com.iberianmotorsports.service;

import com.iberianmotorsports.service.controller.DTO.Mappers.ChampionshipMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.GridDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.GridMapper;
import com.iberianmotorsports.service.repository.*;
import com.iberianmotorsports.service.service.CarService;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.GridService;
import com.iberianmotorsports.service.service.UserService;
import com.iberianmotorsports.service.service.implementation.ChampionshipServiceImpl;
import com.iberianmotorsports.service.service.implementation.GridServiceImpl;
import com.iberianmotorsports.service.service.implementation.ImportDataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImportDataServiceTest {

    private ImportDataServiceImpl importDataService;


    @BeforeEach
    public void init() {
        importDataService = new ImportDataServiceImpl();
    }

    @Nested
    public class importTest {

        @Test
        public void importData() throws Exception {
            importDataService.importData();
        }

    }

}
