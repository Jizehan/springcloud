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
    @Value("${config.info}")
    private String configInfo;

    //修改github文件之后，手动刷新需要运维执行：curl -X POST "http://localhost:3355/actuator/refresh"
    @GetMapping("/configInfo")
    public String getInfo(){
        return configInfo;
    }
}
