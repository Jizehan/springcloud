package com.jizehan.springcloud.service.serviceImpl;

import com.jizehan.springcloud.dao.PaymentDao;
import com.jizehan.springcloud.entities.Payment;
import com.jizehan.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class serviceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    public int create(Payment payment)
    {
        return paymentDao.create(payment);
    }

    public Payment getPaymentById(Long id)
    {
        return paymentDao.getPaymentById(id);
    }
}
