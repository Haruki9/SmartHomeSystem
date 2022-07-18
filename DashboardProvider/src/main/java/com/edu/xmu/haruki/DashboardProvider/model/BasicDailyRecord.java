package com.edu.xmu.haruki.DashboardProvider.model;

import com.edu.xmu.haruki.DashboardProvider.model.sensor.SensorType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * @author haruki_9
 * @date 2022/7/17
 */
@Data
public class BasicDailyRecord {
    private LocalDate recordDate;
    private SensorType type;
    private HashMap<Integer, BasicHourRecord> values;
}
