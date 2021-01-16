package com.imjcker.ddns.dns.cloudflare;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imjcker.ddns.config.DDNSProperties;
import com.imjcker.ddns.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * DDNS Service
 */
@Slf4j
@Service
public class DDNSService {
    private static final String URL = "https://api.cloudflare.com/client/v4/zones/96339b32f2b65f607c5bb90344e989a4/dns_records";
    private final DDNSProperties properties;

    public DDNSService(DDNSProperties properties) {
        this.properties = properties;
    }

    @Scheduled(cron = "${ddns.job.cron}")
    public void ddns() {
        String currentLocalIp = HttpClientUtils.get(properties.getIpFetchUrl());
        log.info("Current local IP address: {}", currentLocalIp);

        Map<String, String> params = new HashMap<>();
        params.put("type", ((DnsCloudflare) properties.getDns()).getParams().getType());
        params.put("name", ((DnsCloudflare) properties.getDns()).getParams().getName());
        String result = HttpClientUtils.get(URL, ((DnsCloudflare) properties.getDns()).getHeaders(), params);
        if (StringUtils.isEmpty(result)) return;
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray resultArray = jsonObject.getJSONArray("result");
        JSONObject o = (JSONObject) resultArray.get(0);
        String id = o.getString("id");
        String domainIp = o.getString("content");
        log.info("Current DNS IP address: {}", domainIp);

        if (!currentLocalIp.trim().equalsIgnoreCase(domainIp.trim())) {
            DnsCloudflare.Params params1 = ((DnsCloudflare) properties.getDns()).getParams();
            JSONObject json = (JSONObject) JSONObject.toJSON(params1);

            json.put("content", currentLocalIp);
            String updateResult = HttpClientUtils.putByJson(URL + "/" + id, ((DnsCloudflare) properties.getDns()).getHeaders(), json);
            JSONObject updated = JSONObject.parseObject(updateResult);
            log.info(updateResult);
            log.info("NDS update result: {}", updated.getBooleanValue("success"));
        }

    }
}
