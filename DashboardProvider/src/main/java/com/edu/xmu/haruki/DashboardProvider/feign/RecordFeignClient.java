package com.edu.xmu.haruki.DashboardProvider.feign;

import com.edu.xmu.haruki.DashboardProvider.model.ResultMsg;
import com.edu.xmu.haruki.DashboardProvider.model.sensor.SensorType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

/**
 * @author haruki_9
 * @date 2022/7/18
 */
@FeignClient("EnvironmentService")
@RequestMapping("/record")
public interface RecordFeignClient {

    @GetMapping("/retrieve")
    ResultMsg retrieveRecords(Long id, Integer sensorId, Integer envId, SensorType type,
                              LocalDateTime startTime,LocalDateTime endTime);
    
    @GetMapping("/exception/retrieve")
    ResultMsg retrieveExceptionRecords(Long id, Integer sensorId, Integer envId, SensorType type,
                                       LocalDateTime startTime,LocalDateTime endTime);
}
