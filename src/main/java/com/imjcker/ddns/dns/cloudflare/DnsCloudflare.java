package com.imjcker.ddns.dns.cloudflare;

import com.imjcker.ddns.core.DNS;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationPropertiesBinding
public class DnsCloudflare extends DNS {
    private Map<String, String> headers = new HashMap<>();
    private Params params = new Params();

    @Data
    @ToString
    @ConfigurationPropertiesBinding
    public static class Params {
        private String type;
        private String name;
        private String ttl;
        private boolean proxied;
    }
}
