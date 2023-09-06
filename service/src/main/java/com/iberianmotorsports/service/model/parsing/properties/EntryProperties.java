package com.iberianmotorsports.service.model.parsing.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "assettocorsa.entry")
public class EntryProperties {
    private Float overrideDriverInfo = 1F;
    private Float isServerAdmin = 0F;
}
