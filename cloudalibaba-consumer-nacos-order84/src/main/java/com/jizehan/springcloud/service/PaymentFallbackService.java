package com.jizehan.springcloud.service;

import com.jizehan.springcloud.entities.CommonResult;
import com.jizehan.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService{
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(444444, "paymentSQL兜底方案");
    }
}
