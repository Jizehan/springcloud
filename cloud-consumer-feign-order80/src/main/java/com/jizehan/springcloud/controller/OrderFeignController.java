package com.jizehan.springcloud.controller;

import com.jizehan.springcloud.entities.CommonResult;
import com.jizehan.springcloud.entities.Payment;
import com.jizehan.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public Object getPaymentById(@PathVariable("id") Long id){
        Object paymentById = paymentFeignService.getPaymentById(id);
        return paymentById;
    }
}
