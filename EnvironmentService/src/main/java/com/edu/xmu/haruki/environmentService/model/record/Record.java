package com.edu.xmu.haruki.environmentService.model.record;

import com.edu.xmu.haruki.environmentService.model.sensor.SensorType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recordTime;

    public LocalDate trimToDate(){
        return recordTime.toLocalDate();
    }

    public LocalTime trimToTime(){
        return recordTime.toLocalTime().withMinute(0).withSecond(0).withNano(0);
    }

}
