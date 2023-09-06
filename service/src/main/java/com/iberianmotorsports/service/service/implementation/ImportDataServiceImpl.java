package com.iberianmotorsports.service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iberianmotorsports.service.model.parsing.Results;
import com.iberianmotorsports.service.service.ImportDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@Transactional
@AllArgsConstructor
public class ImportDataServiceImpl implements ImportDataService {

    @Override
    public void importData() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = "230902_220657_Q.json";
        Results results = objectMapper.readValue(new File(filePath), Results.class);
    }

}
