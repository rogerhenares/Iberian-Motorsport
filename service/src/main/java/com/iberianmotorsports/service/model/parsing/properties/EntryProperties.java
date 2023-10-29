package com.iberianmotorsports.service.model.parsing.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "assettocorsa.entry")
public class EntryProperties {

    private Float overrideDriverInfo = 1F;
    private Integer defaultGridPosition = -1;
    private Integer ballastKg = 0;
    private Integer restrictor = 0;
    private String customCar = "";
    private Integer overrideCarModelForCustomCar = 0;
    private Float isServerAdmin = 0F;

}
