package com.iberianmotorsports.service;

import com.iberianmotorsports.RaceFactory;
import com.iberianmotorsports.service.model.Car;
import com.iberianmotorsports.service.model.parsing.properties.EntryListProperties;
import com.iberianmotorsports.service.model.parsing.properties.EntryProperties;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.ExportDataService;
import com.iberianmotorsports.service.service.GridService;
import com.iberianmotorsports.service.service.ImportDataService;
import com.iberianmotorsports.service.service.implementation.ExportDataServiceImpl;
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
        exportDataService = new ExportDataServiceImpl();
    }

    @Test
    public void exportData() throws Exception {
        EntryProperties entryProperties = new EntryProperties();
        EntryListProperties entryListProperties = new EntryListProperties();

//        exportDataService.exportData(RaceFactory.race(), entryProperties, entryListProperties);

    }

}
