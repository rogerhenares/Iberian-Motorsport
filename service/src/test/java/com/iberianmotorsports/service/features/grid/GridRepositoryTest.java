package com.iberianmotorsports.service.features.grid;

import com.iberianmotorsports.GridFactory;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.repository.GridRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:it.properties")
public class GridRepositoryTest {

    @Autowired
    private GridRepository gridRepository;

    @BeforeEach
    public void setupDataBase() { Grid grid = GridFactory.grid();}

    @Test
    void save() {
        Grid grid = GridFactory.grid();
        grid.setId(null);
        Grid savedGrid = gridRepository.save(grid);
        assertEquals(savedGrid, grid);
    }

    @Test
    void getGridByChampionshipId() {
        Grid grid = GridFactory.grid();
        Long championshipId = grid.getChampionship().getId();
        List<Grid> foundGrid = gridRepository.findGridsByChampionshipId(championshipId);
        assertEquals(foundGrid.get(0), grid);
    }

}
