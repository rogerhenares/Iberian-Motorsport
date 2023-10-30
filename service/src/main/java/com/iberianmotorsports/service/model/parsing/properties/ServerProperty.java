package com.iberianmotorsports.service.model.parsing.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "assettocorsa.server")
public class ServerProperty {

    private String folder;
    private String serverName;
    private String category;
    private String defaultFilesFolder;
    private Boolean cronEnabled;

}
