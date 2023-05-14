package com.iberianmotorsports.service.features.race;

import com.iberianmotorsports.RaceFactory;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.repository.RaceRepository;
import com.iberianmotorsports.service.service.RaceService;
import com.iberianmotorsports.service.service.implementation.RaceServiceImpl;
import org.apache.commons.io.FileUtils;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RaceServiceTest {

    private RaceService service;

    @Mock
    private RaceRepository raceRepository;

    @Captor
    ArgumentCaptor<Race> raceCaptor;

    @BeforeEach
    public void init() {
        service = new RaceServiceImpl(raceRepository);
    }

    @Nested
    class saveRace {

        @Test
        public void saveRace() {
            Race testRace = RaceFactory.race();
            givenRaceRepositorySave();

            service.saveRace(testRace);

            verify(raceRepository).save(raceCaptor.capture());
            assertEquals(RaceFactory.race(), raceCaptor.getValue());
        }

        @Test
        public void saveDuplicateRace() {
            Race testRace = RaceFactory.race();
            givenRaceAlreadyExists();

            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> service.saveRace(testRace));

            verify(raceRepository, times(0)).save(any());
            Assertions.assertEquals(ErrorMessages.DUPLICATED_RACE.getDescription(), exception.getMessage());
        }

    }

    @Nested
    class updateRace {

        @Test
        public void updateRace() {
            Race testRace = RaceFactory.race();
            givenRaceRepositorySave();

            service.updateRace(testRace);

            verify(raceRepository).save(raceCaptor.capture());
            assertEquals(RaceFactory.race(), raceCaptor.getValue());

        }
    }


    @Nested
    class deleteRace {

        @Test
        public void deleteRace() {

            service.deleteRace(anyLong());

            verify(raceRepository).deleteById(anyLong());
        }
    }

    @Nested
    class findRace {

        @Test
        public void findRaceById() {
            when(raceRepository.findById(anyLong())).thenReturn(Optional.of(RaceFactory.race()));

            raceRepository.findById(anyLong());

            verify(raceRepository).findById(anyLong());
        }

        @Test
        public void findRaceByName() {
            when(raceRepository.findByName(anyString())).thenReturn(Optional.of(RaceFactory.race()));

            raceRepository.findByName(anyString());

            verify(raceRepository).findByName(anyString());
        }

        @Test
        public void findRaceInvalidId() {
            when(raceRepository.findById(anyLong())).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> service.findRaceById(anyLong()));

            verify(raceRepository).findById(anyLong());
            Assertions.assertEquals(ErrorMessages.RACE_NOT_IN_DB.getDescription(), exception.getMessage());
        }

        @Test
        public void findRaceInvalidName() {
            when(raceRepository.findByName(anyString())).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(ServiceException.class, ()-> service.findRaceByName(anyString()));

            verify(raceRepository).findByName(anyString());
            assertEquals(ErrorMessages.RACE_NOT_IN_DB.getDescription(), exception.getMessage());
        }
    }

    @Test
    public void exportRace() throws IOException {
        Race testRace = RaceFactory.race();

        String result = service.exportRace(testRace);

        assertTrue(result.startsWith("Race saved to"));

        Path filePath = Paths.get(result.substring("Race saved to ".length()));

        FileUtils.deleteQuietly(filePath.toFile());
    }


    private void givenRaceRepositorySave() {
        when(raceRepository.save(any(Race.class))).then((Answer<Race>) invocation -> {
            Race race = invocation.getArgument(0);
            race.setId(1L);
            return race;
        });
    }

    private void givenRaceAlreadyExists() {
        when(raceRepository.findById(anyLong())).thenReturn(Optional.of(RaceFactory.race()));
    }

}
