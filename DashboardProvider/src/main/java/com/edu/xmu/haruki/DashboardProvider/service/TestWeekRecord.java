package com.edu.xmu.haruki.DashboardProvider.service;

import com.edu.xmu.haruki.DashboardProvider.model.Record;
import com.edu.xmu.haruki.DashboardProvider.model.sensor.SensorType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author haruki_9
 * @date 2022/7/18
 */
public class TestWeekRecord {
    public static void main(String[] args) {
        List<Record> records= Arrays.asList(
                new Record(1L,1,1, SensorType.TEMPERATURE,25.1, LocalDateTime.of(2022,7,18,1,0,0)),
                new Record(1L,1,1, SensorType.TEMPERATURE,24.1, LocalDateTime.of(2022,7,18,2,0,0)),
                new Record(1L,1,1, SensorType.TEMPERATURE,23.1, LocalDateTime.of(2022,7,17,2,0,0)),
                new Record(1L,1,1, SensorType.TEMPERATURE,26.1, LocalDateTime.of(2022,7,17,3,0,0)),
                new Record(1L,1,1, SensorType.TEMPERATURE,29.1, LocalDateTime.of(2022,7,16,1,0,0)),
                new Record(1L,1,1, SensorType.TEMPERATURE,22.1, LocalDateTime.of(2022,7,16,1,2,0)),
                new Record(1L,1,1, SensorType.TEMPERATURE,21.1, LocalDateTime.of(2022,7,16,1,3,0))
        );
        Map<LocalDate, Map<LocalTime, Double>> str=records.stream().collect(
                Collectors.groupingBy(
                        Record::getDate,
                        Collectors.groupingBy(Record::getTime,Collectors.averagingDouble(Record::getSenseValue))
                )
        );

        Map<LocalDate, DoubleSummaryStatistics> weekMax=records.stream().collect(
                Collectors.groupingBy(
                        Record::getDate,
                        Collectors.summarizingDouble(Record::getSenseValue)
                )
        );

        System.out.println(weekMax);

        System.out.println(str);
    }
}
