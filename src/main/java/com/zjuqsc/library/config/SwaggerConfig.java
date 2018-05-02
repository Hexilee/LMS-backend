package com.zjuqsc.library.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * @author Li Chenxi
 */
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig {
    @Data
    public static class Info {
        private String version;
        private String title;
        private String description;
        private String license;
        private String licenseUrl;
        private String contactName;
        private String contactURL;
        private String contactEmail;
    }

    @NotNull private Info info;

}
