package com.iberianmotorsports.service.features.championship;

import com.iberianmotorsports.ChampionshipFactory;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.repository.ChampionshipRepository;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.implementation.ChampionshipServiceImpl;
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
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ChampionshipServiceTest {


    private ChampionshipService service;

    @Mock
    private ChampionshipRepository championshipRepository;

    @Captor
    ArgumentCaptor<Championship> championshipCaptor;

    @BeforeEach
    public void init() {
        service = new ChampionshipServiceImpl(championshipRepository);
    }

    @Nested
    class saveChampionship {
        @Test
        public void saveChampionship() {
            Championship testChampionship = ChampionshipFactory.championship();
            givenChampionshipRepositorySave();

            service.saveChampionship(testChampionship);

            verify(championshipRepository).save(championshipCaptor.capture());
            assertEquals(ChampionshipFactory.championship(), championshipCaptor.getValue());
        }

        @Test
        public void saveDuplicateChampionship() {
            Championship testChampionship = ChampionshipFactory.championship();
            givenChampionshipAlreadyExists();

            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> service.saveChampionship(testChampionship));

            verify(championshipRepository, times(0)).save(any());
            assertEquals(ErrorMessages.DUPLICATED_CHAMPIONSHIP.getDescription(), exception.getMessage());
        }

    }
    @Nested
    class updateChampionship {

        @Test
        public void updateChampionship() {
            Championship testChampionship = ChampionshipFactory.championship();
            givenChampionshipRepositorySave();

            service.updateChampionship(testChampionship);

            verify(championshipRepository).save(championshipCaptor.capture());
            assertEquals(ChampionshipFactory.championship(), championshipCaptor.getValue());

        }
    }

    @Nested
    class deleteChampionship {

        @Test
        public void deleteChampionship() {

            service.deleteChampionship(anyLong());

            verify(championshipRepository).deleteById(anyLong());
        }
    }

    @Nested
    class findChampionship {

        @Test
        public void findChampionshipById() {
            when(championshipRepository.findById(anyLong())).thenReturn(Optional.of(ChampionshipFactory.championship()));

            championshipRepository.findById(anyLong());

            verify(championshipRepository).findById(anyLong());
        }

        @Test
        public void findChampionshipByName() {
            when(championshipRepository.findByName(anyString())).thenReturn(Optional.of(ChampionshipFactory.championship()));

            championshipRepository.findByName(anyString());

            verify(championshipRepository).findByName(anyString());
        }

        @Test
        public void findChampionshipInvalidId() {
            when(championshipRepository.findById(anyLong())).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> service.findChampionshipById(anyLong()));

            verify(championshipRepository).findById(anyLong());
            Assertions.assertEquals(ErrorMessages.CHAMPIONSHIP_NOT_IN_DB.getDescription(), exception.getMessage());
        }

        @Test
        public void findChampionshipInvalidName() {
            when(championshipRepository.findByName(anyString())).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(ServiceException.class, ()-> service.findChampionshipByName(anyString()));

            verify(championshipRepository).findByName(anyString());
            assertEquals(ErrorMessages.CHAMPIONSHIP_NOT_IN_DB.getDescription(), exception.getMessage());
        }
    }

    @Test
    public void exportChampionship() throws IOException {
        Championship testChampionship = ChampionshipFactory.championship();
        String result = service.exportChampionship(testChampionship);
        assertTrue(result.startsWith("Championship saved to"));

        Path filePath = Paths.get(result.substring("Championship saved to ".length()));
        FileUtils.deleteQuietly(filePath.toFile());
    }


    private void givenChampionshipRepositorySave() {
        when(championshipRepository.save(any(Championship.class))).then((Answer<Championship>) invocation -> {
            Championship championship = invocation.getArgument(0);
            championship.setId(1L);
            return championship;
        });
    }

    private void givenChampionshipAlreadyExists() {
        when(championshipRepository.findByName(anyString())).thenReturn(Optional.of(ChampionshipFactory.championship()));
    }

}

