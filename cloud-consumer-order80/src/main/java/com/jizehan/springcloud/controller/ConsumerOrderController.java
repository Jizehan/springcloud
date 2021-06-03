package com.jizehan.springcloud.controller;

import com.jizehan.springcloud.entities.CommonResult;
import com.jizehan.springcloud.entities.Payment;
import com.jizehan.springcloud.lb.MyLoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class ConsumerOrderController {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private MyLoadBalance myLoadBalance;
    @Resource
    private DiscoveryClient discoveryClient;

//    public static final String PAYMENT_URL = "http://localhost:8001";
    //通过euraka暴露的服务名称访问支付服务，背后有两台服务
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    @GetMapping("consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            log.info(entity.getHeaders() + "\t" + entity.getBody());
            return entity.getBody();
        }else {
            return new CommonResult<>(444, "失败");
        }
    }

    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLb(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() == 0) {
            return null;
        }
        ServiceInstance instance = myLoadBalance.instance(instances);
        return restTemplate.getForObject(instance.getUri() + "/payment/lb", String.class);
    }

}
