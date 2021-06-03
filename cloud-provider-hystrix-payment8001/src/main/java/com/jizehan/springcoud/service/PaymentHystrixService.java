package com.jizehan.springcoud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PaymentHystrixService {

    public String paymentInfo_ok(Integer id){
        return "线程池" + Thread.currentThread().getName() + " paymentInfo_ok,id:" + id + "\t  O(∩_∩)O哈哈~";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id) throws InterruptedException {
        int timeOut = 1;
//        int i = 10 / 0;
        TimeUnit.SECONDS.sleep(timeOut);
        return "线程池" + Thread.currentThread().getName() + " paymentInfo_TimeOut,id:" + id + "\t  O(∩_∩)O哈哈~,耗时（秒）；";
    }

    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池" + Thread.currentThread().getName() + " paymentInfo_TimeOutHandler系统繁忙请稍后再试,id:" + id + "\t  /(ㄒoㄒ)/~~";
    }



    //======服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
    })//时间窗口期内10次的错误率到达60%开启短路
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id < 0) {
            throw new RuntimeException("********id不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数，请稍后再试，id：" + id;
    }
}
