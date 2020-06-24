package com.imjcker.ddns;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import java.util.HashMap;
import java.util.Map;

/**
 * cloudflare DDNS API configuration
 */
@Data
@ConfigurationProperties(prefix = "ddns")
public class DDNSProperties {
    private Job job;
    @URL
    private String ipFetchUrl;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, Object> params = new HashMap<>();

    /**
     * schedule configuration
     */
    @Data
    @ConfigurationPropertiesBinding
    public static class Job {
        private String cron;
    }
}
