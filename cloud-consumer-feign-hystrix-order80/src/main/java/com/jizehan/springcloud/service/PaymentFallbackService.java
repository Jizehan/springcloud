package com.jizehan.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "--------paymentInfo_OK fallback /(ćoć)/~~";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) throws InterruptedException {
        return "--------paymentInfo_TimeOut fallback /(ćoć)/~~";
    }
}
