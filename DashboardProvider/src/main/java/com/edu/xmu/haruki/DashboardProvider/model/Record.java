package com.edu.xmu.haruki.DashboardProvider.model;

import com.edu.xmu.haruki.DashboardProvider.model.sensor.SensorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author haruki_9
 * @date 2022/7/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    private Long id;
    private Integer sensorId;
    private Integer envId;
    private SensorType sensorType;
    private Double senseValue;

    private LocalDateTime recordTime;

    public LocalDate getDate(){
        return recordTime.toLocalDate();
    }

    public LocalTime getTime(){
        return recordTime.toLocalTime().withMinute(0).withSecond(0).withNano(0);
    }

}
