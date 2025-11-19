package com.info.configdemo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "build")
@Data
public class BuildInfo {
    private String id;
    private String version;
    private String name;
    private String type;
}