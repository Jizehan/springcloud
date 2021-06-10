package com.jizehan.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RefreshScope
public class ConfigClientController {
    @Value("${server.port}")
    private String serverPort;

    @Value("${config.info}")
    private String configInfo;

    /**
     * cmd执行 curl -X POST "http://localhost:3344/actuator/bus-refresh" 全部刷新
     * 定点刷新  curl -X POST "http://localhost:3344/actuator/bus-refresh/config-client:3355
     * @return
     */

    @GetMapping("/configInfo")
    public String configInfo()
    {
        return "serverPort: "+serverPort+"\t\n\n configInfo: "+configInfo;
    }
}
