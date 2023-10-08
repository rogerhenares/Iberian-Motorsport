package com.iberianmotorsports.service.model.parsing.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "steam.client")
public class SteamClientProperties {

    String token;
    String host;
}
