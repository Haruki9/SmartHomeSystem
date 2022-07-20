package com.edu.xmu.haruki.environmentService.model.record;

import com.edu.xmu.haruki.environmentService.model.sensor.SensorType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author haruki_9
 * @date 2022/7/14
 */
@Data
public class RecordVo {
    private Long id;
    private Integer sensorId;
    private Integer envId;
    private SensorType sensorType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
