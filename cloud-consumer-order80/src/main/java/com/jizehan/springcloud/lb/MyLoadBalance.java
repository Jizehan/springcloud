package com.jizehan.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MyLoadBalance {
    ServiceInstance instance(List<ServiceInstance> serviceInstances);
}
