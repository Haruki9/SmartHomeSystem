package com.edu.xmu.haruki.environmentService.model.record;

import com.edu.xmu.haruki.environmentService.model.sensor.SensorType;
import lombok.Data;

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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
