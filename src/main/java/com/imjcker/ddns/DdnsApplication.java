package com.imjcker.ddns;

import com.imjcker.ddns.config.DDNSProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({DDNSProperties.class})
public class DdnsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DdnsApplication.class, args);
    }
}
