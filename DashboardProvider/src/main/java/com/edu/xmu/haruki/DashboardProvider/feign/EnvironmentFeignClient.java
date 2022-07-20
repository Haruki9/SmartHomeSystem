package com.edu.xmu.haruki.DashboardProvider.feign;

import com.edu.xmu.haruki.DashboardProvider.model.ResultMsg;
import com.edu.xmu.haruki.DashboardProvider.model.sensor.SensorType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

/**
 * @author haruki_9
 * @date 2022/7/17
 */
@FeignClient(name = "EnvironmentService")
public interface EnvironmentFeignClient {

    @GetMapping("/environment/available")
    ResultMsg allAvailableEnvironment();

    @GetMapping("/environment/{envId}/realtime")
    ResultMsg envRealTimeSituation(@PathVariable Integer envId);

    @GetMapping("/record/retrieve")
    ResultMsg retrieveRecords(@RequestParam Long id, @RequestParam Integer sensorId, @RequestParam Integer envId, @RequestParam SensorType type,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime);

    @GetMapping("/record/exception/retrieve")
    ResultMsg retrieveExceptionRecords(@RequestParam Long id,@RequestParam Integer sensorId,@RequestParam Integer envId,@RequestParam SensorType type,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime);
}
