package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.Race;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface ExportDataService {

    void exportData(Race race) throws Exception;

    File getAccServerDir(Race race);
}
