package com.iberianmotorsports.service;

import com.iberianmotorsports.service.controller.DTO.Mappers.ChampionshipMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.GridDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.GridMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceMapper;
import com.iberianmotorsports.service.repository.*;
import com.iberianmotorsports.service.service.*;
import com.iberianmotorsports.service.service.implementation.ChampionshipServiceImpl;
import com.iberianmotorsports.service.service.implementation.GridServiceImpl;
import com.iberianmotorsports.service.service.implementation.ImportDataServiceImpl;
import com.iberianmotorsports.service.service.implementation.RaceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImportDataServiceTest {

    private ImportDataServiceImpl importDataService;
    private ChampionshipService championshipService;
    private RaceService raceService;
    private RaceRepository raceRepository;
    private RaceMapper raceMapper;
    private SessionService sessionService;
    private RaceRulesService raceRulesService;


    @BeforeEach
    public void init() {
        raceService = new RaceServiceImpl(championshipService, raceRepository, raceMapper, sessionService, raceRulesService);
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
