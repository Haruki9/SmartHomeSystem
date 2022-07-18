package com.edu.xmu.haruki.DashboardProvider.feign;

import com.edu.xmu.haruki.DashboardProvider.model.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author haruki_9
 * @date 2022/7/17
 */
@FeignClient(name = "EnvironmentService")
@RequestMapping("environment")
public interface EnvironmentFeignClient {

    @GetMapping("/available")
    ResultMsg allAvailableEnvironment();

    @GetMapping("/{envId}/realtime")
    ResultMsg envRealTimeSituation(@PathVariable Integer envId);
}
