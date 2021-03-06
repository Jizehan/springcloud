package com.jizehan.springcloud;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置路由的第二种方式
 */
@Configuration
public class GateWayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_jizehan", r -> r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
        routes.route("path_route_jizehan1", r -> r.path("/guoji").uri("http://news.baidu.com/guoji")).build();
        return routes.build();
    }


}
