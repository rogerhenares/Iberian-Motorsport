package com.iberianmotorsports.service;

import com.iberianmotorsports.RaceFactory;
import com.iberianmotorsports.service.model.Car;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.ExportDataService;
import com.iberianmotorsports.service.service.GridService;
import com.iberianmotorsports.service.service.ImportDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExportDataServiceTest {

    private ExportDataService exportDataService;
    private ImportDataService importDataService;
    private ChampionshipService championshipService;
    private GridService gridService;

    @Captor
    ArgumentCaptor<Car> carCaptor;

    @BeforeEach
    public void init() {

    }

    @Test
    public void exportData() throws Exception {

        exportDataService.exportData(RaceFactory.race());

    }

}
