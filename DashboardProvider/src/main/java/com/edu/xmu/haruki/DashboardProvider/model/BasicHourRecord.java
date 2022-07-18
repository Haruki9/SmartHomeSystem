package com.edu.xmu.haruki.DashboardProvider.model;


import com.edu.xmu.haruki.DashboardProvider.model.sensor.SensorType;
import lombok.Data;

import java.time.LocalTime;

/**
 * @author haruki_9
 * @date 2022/7/18
 */
@Data
public class BasicHourRecord {
    private LocalTime recordHour;
    private SensorType type;
    private Double value;
}
