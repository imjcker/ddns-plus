package com.imjcker.ddns.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@Data
@ConfigurationPropertiesBinding
public class DNS {
    private String name;
}
