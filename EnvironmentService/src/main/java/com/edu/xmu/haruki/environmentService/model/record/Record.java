package com.edu.xmu.haruki.environmentService.model.record;

import com.edu.xmu.haruki.environmentService.model.sensor.SensorType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author haruki_9
 * @date 2022/7/13
 */
@Data
public class Record {
    private Long id;
    private Integer sensorId;
    private Integer envId;
    private SensorType sensorType;
    private BigDecimal senseValue;
    private LocalDateTime recordTime;
}
