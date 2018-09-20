package com.stool.gram.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "resource")
public class ResourceConfig {

    private String modelRootPath;

    public String getModelRootPath() {
        return modelRootPath;
    }

    public void setModelRootPath(String modelRootPath) {
        this.modelRootPath = modelRootPath;
    }
}
