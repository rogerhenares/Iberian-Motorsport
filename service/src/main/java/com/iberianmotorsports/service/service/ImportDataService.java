package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.Race;
import org.springframework.stereotype.Service;

@Service
public interface ImportDataService {

    void importData(Race race) throws Exception;

}
