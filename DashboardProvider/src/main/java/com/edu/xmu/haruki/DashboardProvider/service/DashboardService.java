package com.edu.xmu.haruki.DashboardProvider.service;

import com.edu.xmu.haruki.DashboardProvider.feign.EnvironmentFeignClient;
import com.edu.xmu.haruki.DashboardProvider.model.Record;
import com.edu.xmu.haruki.DashboardProvider.model.ResultMsg;
import com.edu.xmu.haruki.DashboardProvider.model.sensor.SensorType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author haruki_9
 * @date 2022/7/17
 */
@Service
public class DashboardService {

    @Autowired
    private EnvironmentFeignClient environmentFeignClient;

    public Object allAvailableEnvs(){
        return environmentFeignClient.allAvailableEnvironment();
    }

    public Object realTimeEnvSituation(Integer envId){
        return environmentFeignClient.envRealTimeSituation(envId);
    }


    /**
     * 获取近一周的温度情况
     * 首先获取近一周的所有温度记录
     * 然后用lambda进行数据处理
     *
     * map-> 将每个记录的时间map到一个整点时刻
     * groupBy -> 根据时刻进行数据聚合，得到24个时刻的温度数据（list of record）
     * max 找到最高温度
     * min 找到最低温度
     *
     *
     * 每日温度格式
     * daily temperature：
     *      max： 最高温
     *      min： 最低温
     *      list hour temperature: 24个时刻数据
     *
     * 最后返回ResultMsg，格式如下：
     * start day：开始日
     * end day： 结束日
     * data： List of daily temperature：时间段内的每日温度数据
     *
     * @param envId
     * @return
     */
    public ResultMsg weeklyTemperature(Integer envId, LocalDate startDate, LocalDate endDate){
        ResultMsg msg1=environmentFeignClient.retrieveRecords(null,null,envId,SensorType.TEMPERATURE, startDate.atStartOfDay(),endDate.atStartOfDay());

        ObjectMapper objectMapper=new ObjectMapper().registerModule(new JavaTimeModule());
        List<Record> records=objectMapper.convertValue(msg1.getData(), new TypeReference<>() {});

        Map<LocalDate, Map<LocalTime, Double>> weeklyTemperatureAvg=records.stream().collect(Collectors.groupingBy(
                Record::getDate,Collectors.groupingBy(
                        Record::getTime, Collectors.averagingDouble(Record::getSenseValue)
                )
        ));

        Map<LocalDate, DoubleSummaryStatistics> weeklyTemperatureStatistic=records.stream().collect(Collectors.groupingBy(
                Record::getDate,Collectors.summarizingDouble(Record::getSenseValue)
                ));

        HashMap<String,Object> data=new HashMap<>();
        data.put("DailyTemperatureStatistic",weeklyTemperatureStatistic);
        data.put("HourlyTemperatureAvg",weeklyTemperatureAvg);
        ResultMsg msg=new ResultMsg();
        msg.setCode(200);
        msg.setMsg("查询成功！");
        msg.setData(data);
        return msg;
    }

    public ResultMsg humidityStatistic(Integer envId, LocalDate startDate, LocalDate endDate) {

        ResultMsg msg1=environmentFeignClient.retrieveRecords(null,null,envId,SensorType.HUMIDITY, startDate.atStartOfDay(),endDate.atStartOfDay());

        ObjectMapper objectMapper=new ObjectMapper().registerModule(new JavaTimeModule());
        List<Record> humidityRecords=objectMapper.convertValue(msg1.getData(), new TypeReference<>() {
        });
        Map<LocalDate, DoubleSummaryStatistics> statistics=humidityRecords.stream().collect(Collectors.groupingBy(
                Record::getDate,
                Collectors.summarizingDouble(Record::getSenseValue)
        ));

        ResultMsg msg=new ResultMsg();
        HashMap<String,Object> data=new HashMap<>();
        data.put("DailyHumidityStatistic",statistics);
        msg.setCode(200);
        msg.setMsg("查询成功！");
        msg.setData(data);
        return msg;
    }

    public ResultMsg exceptionStatistic(Integer envId, LocalDate startDate, LocalDate endDate) {
        ResultMsg msg1=environmentFeignClient.retrieveExceptionRecords(null,null,envId,null, startDate.atStartOfDay(),endDate.atStartOfDay());

        ObjectMapper objectMapper=new ObjectMapper().registerModule(new JavaTimeModule());
        List<Record> exceptionRecords=objectMapper.convertValue(msg1.getData(), new TypeReference<>() {
        });
        Map<SensorType, Long> countStatistic=exceptionRecords.stream().collect(
                Collectors.groupingBy(
                        Record::getSensorType,
                        Collectors.counting()
                )
        );


        ResultMsg msg=new ResultMsg();
        HashMap<String,Object> data=new HashMap<>();
        data.put("ExceptionCountStatistic",countStatistic);
        msg.setCode(200);
        msg.setMsg("查询成功！");
        msg.setData(data);
        return msg;
    }

    public ResultMsg exceptionRecords(Integer envId, LocalDate startDate, LocalDate endDate) {
        List<Record> exceptionRecords= (List<Record>) environmentFeignClient.retrieveExceptionRecords(null,null,envId,null,startDate.atStartOfDay(),endDate.atStartOfDay()).getData();
        ResultMsg msg=new ResultMsg();
        HashMap<String,Object> data=new HashMap<>();
        data.put("exceptionRecords",exceptionRecords);
        msg.setCode(200);
        msg.setMsg("查询成功！");
        msg.setData(data);
        return msg;
    }
}
