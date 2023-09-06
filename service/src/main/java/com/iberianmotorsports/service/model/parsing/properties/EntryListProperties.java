package com.iberianmotorsports.service.model.parsing.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "assettocorsa.entrylist")
public class EntryListProperties {

    private Float forceEntryList = 0F;

}
