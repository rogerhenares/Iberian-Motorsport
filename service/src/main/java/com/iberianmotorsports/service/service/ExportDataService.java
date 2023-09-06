package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.parsing.properties.EntryListProperties;
import com.iberianmotorsports.service.model.parsing.properties.EntryProperties;
import org.springframework.stereotype.Service;

@Service
public interface ExportDataService {

    void exportData(Race race, EntryProperties entryProperties, EntryListProperties entryListProperties) throws Exception;

}
