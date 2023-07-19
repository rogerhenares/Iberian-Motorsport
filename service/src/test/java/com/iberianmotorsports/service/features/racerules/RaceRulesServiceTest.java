package com.iberianmotorsports.service.features.racerules;

import com.iberianmotorsports.RaceFactory;
import com.iberianmotorsports.RaceRulesFactory;
import com.iberianmotorsports.SessionFactory;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceRulesDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.RaceRulesMapper;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.RaceRules;
import com.iberianmotorsports.service.repository.RaceRulesRepository;
import com.iberianmotorsports.service.service.RaceRulesService;
import com.iberianmotorsports.service.service.implementation.RaceRulesServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RaceRulesServiceTest {

    private RaceRulesService service;

    @Mock
    private RaceRulesRepository raceRulesRepository;

    private RaceRulesMapper raceRulesMapper;

    private RaceRulesDTOMapper raceRulesDTOMapper;

    @Captor
    ArgumentCaptor<RaceRules> raceRulesCaptor;

    @BeforeEach
    public void init() {
        raceRulesDTOMapper = new RaceRulesDTOMapper();
        raceRulesMapper = new RaceRulesMapper();
        service = new RaceRulesServiceImpl(raceRulesRepository, raceRulesMapper, raceRulesDTOMapper);
    }

    @Nested
    class saveRaceRules {

        @Test
        public void saveRaceRules() {
            RaceRules testRaceRules = RaceRulesFactory.raceRules();
            Race testRace = RaceFactory.race();
            givenRaceRulesRepositorySave();

            service.saveRaceRules(raceRulesDTOMapper.apply(testRaceRules), testRace);

            verify(raceRulesRepository).save(raceRulesCaptor.capture());
            assertEquals(RaceRulesFactory.raceRules(), raceRulesCaptor.getValue());
        }

        @Test
        public void saveDuplicateRaceRules() {
            RaceRules testRaceRules = RaceRulesFactory.raceRules();
            Race testRace = RaceFactory.race();
            givenRaceRulesAlreadyExists();

            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> service.saveRaceRules(raceRulesDTOMapper.apply(testRaceRules), testRace));

            verify(raceRulesRepository, times(0)).save(any());
            Assertions.assertEquals(ErrorMessages.DUPLICATED_RACE_RULES.getDescription(), exception.getMessage());
        }

    }

    @Nested
    class updateRaceRules {

        @Test
        public void updateRaceRules() {
            RaceRules testRaceRules = RaceRulesFactory.raceRules();
            givenRaceRulesRepositorySave();

            service.updateRaceRules(testRaceRules);

            verify(raceRulesRepository).save(raceRulesCaptor.capture());
            assertEquals(RaceRulesFactory.raceRules(), raceRulesCaptor.getValue());

        }
    }


    @Nested
    class deleteRaceRules {

        @Test
        public void deleteRaceRules() {

            service.deleteRaceRules(anyLong());

            verify(raceRulesRepository).deleteById(anyLong());
        }
    }

    @Nested
    class findRaceRules {

        @Test
        public void findRaceRulesById() {
            when(raceRulesRepository.findById(anyLong())).thenReturn(Optional.of(RaceRulesFactory.raceRules()));

            raceRulesRepository.findById(anyLong());

            verify(raceRulesRepository).findById(anyLong());
        }


        @Test
        public void findRaceRulesInvalidId() {
            when(raceRulesRepository.findById(anyLong())).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> service.findRaceRulesById(anyLong()));

            verify(raceRulesRepository).findById(anyLong());
            Assertions.assertEquals(ErrorMessages.RACE_RULES_NOT_IN_DB.getDescription(), exception.getMessage());
        }


    }

    @Test
    @Disabled
    public void exportRaceRules() throws IOException {
        RaceRules testRaceRules = RaceRulesFactory.raceRules();

        String result = service.exportRaceRules(testRaceRules);

        assertTrue(result.startsWith("Race rules saved to"));

        Path filePath = Paths.get(result.substring("Race rules saved to ".length()));
    }


    private void givenRaceRulesRepositorySave() {
        when(raceRulesRepository.save(any(RaceRules.class))).then((Answer<RaceRules>) invocation -> {
            RaceRules raceRules = invocation.getArgument(0);
            raceRules.setId(1L);
            return raceRules;
        });
    }

    private void givenRaceRulesAlreadyExists() {
        when(raceRulesRepository.findById(anyLong())).thenReturn(Optional.of(RaceRulesFactory.raceRules()));
    }


}
