package com.imjcker.ddns;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    private static final String URL = "https://api.cloudflare.com/client/v4/zones/96339b32f2b65f607c5bb90344e989a4/dns_records";

    @Scheduled(cron = "0 */2 * * * ?")
    public void ddns() {
        String currentLocalIp = HttpClientUtils.get("http://ip.sb");
        log.info("Current local IP address: {}", currentLocalIp);

        Map<String, String> headers = new HashMap<>();
        headers.put("X-auth-Email", "helloalanturing@icloud.com");
        headers.put("X-auth-Key", "639164cf0ab9e7652ad48ce90c061e787cfa8");
        headers.put("Content-Type", "application/json");

        Map<String, String> params = new HashMap<>();
        params.put("type", "A");
        params.put("name", "imjcker.com");
        String result = HttpClientUtils.get(URL, headers, params);

        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray resultArray = jsonObject.getJSONArray("result");
        JSONObject o = (JSONObject) resultArray.get(0);
        String id = o.getString("id");
        String domainIp = o.getString("content");
        log.info("Current DNS IP address: {}", domainIp);

        if (!currentLocalIp.trim().equalsIgnoreCase(domainIp.trim())) {
            JSONObject json = new JSONObject();
            json.put("type", "A");
            json.put("name", "imjcker.com");
            json.put("content", currentLocalIp);
            json.put("ttl", 120);
            json.put("proxied", false);
            String updateResult = HttpClientUtils.putByJson(URL + "/" + id, headers, json);

            JSONObject updated = JSONObject.parseObject(updateResult);
            log.info("NDS update result: {}", updated.getBooleanValue("success"));
        }

    }
}
