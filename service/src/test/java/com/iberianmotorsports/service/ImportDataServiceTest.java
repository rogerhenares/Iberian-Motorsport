package com.iberianmotorsports.service;

import com.iberianmotorsports.ChampionshipFactory;
import com.iberianmotorsports.RaceFactory;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceRulesMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.SessionMapper;
import com.iberianmotorsports.service.model.GridRace;
import com.iberianmotorsports.service.repository.GridRaceRepository;
import com.iberianmotorsports.service.repository.RaceRepository;
import com.iberianmotorsports.service.repository.SanctionRepository;
import com.iberianmotorsports.service.service.*;
import com.iberianmotorsports.service.service.implementation.ImportDataServiceImpl;
import com.iberianmotorsports.service.service.implementation.RaceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:it.properties")
public class ImportDataServiceTest {

    @InjectMocks
    private ImportDataServiceImpl importDataService;
    @Mock
    private ChampionshipService championshipService;
    @Mock
    private RaceService raceService;
    @Mock
    private GridRaceRepository gridRaceRepository;
    @Mock
    private RaceRepository raceRepository;
    private RaceMapper raceMapper;
    @Mock
    private SessionService sessionService;
    @Mock
    private RaceRulesService raceRulesService;
    @Mock
    private SessionMapper sessionMapper;
    @Mock
    private RaceRulesMapper raceRulesMapper;
    @Mock
    private GridRaceService gridRaceService;
    @Mock
    private SanctionRepository sanctionRepository;

    @Value("#{'${pointsSystem}'}")
    private List<Integer> pointsSystem;

    @Value("#{'${qualyPoints}'}")
    private List<Integer> qualyPoints;

    @Value("${assettocorsa.folder.import.filepath}")
    private String filePath;


    @BeforeEach
    public void init() {
        raceService = new RaceServiceImpl(championshipService, raceRepository, raceMapper, sessionService, raceRulesService, sessionMapper, raceRulesMapper);
        importDataService = new ImportDataServiceImpl(championshipService, raceService, gridRaceService, sanctionRepository, pointsSystem, qualyPoints);
    }

    @Nested
    public class importTest {

        @Test
        public void importData() throws Exception {

            ArgumentCaptor<GridRace> gridRaceCaptor = ArgumentCaptor.forClass(GridRace.class);

            when(championshipService.findChampionshipById(anyLong())).thenReturn(ChampionshipFactory.championship());
            when(raceRepository.findById(anyLong())).thenReturn(Optional.of(RaceFactory.race()));

            importDataService.importData();

            verify(gridRaceService, times(2)).saveGridRace(gridRaceCaptor.capture());


            List<GridRace> capturedGridRaces = gridRaceCaptor.getAllValues();

            for (GridRace capturedGridRace : capturedGridRaces) {
                System.out.println("Captured GridRace: " + capturedGridRace);

            }

        }

    }
}

