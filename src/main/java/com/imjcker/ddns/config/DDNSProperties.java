package com.imjcker.ddns.config;

import com.imjcker.ddns.core.DNS;
import lombok.Data;
import lombok.ToString;
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

    private DNS dns;

    /**
     * schedule configuration
     */
    @Data
    @ConfigurationPropertiesBinding
    public static class Job {
        private String cron;
    }


}
