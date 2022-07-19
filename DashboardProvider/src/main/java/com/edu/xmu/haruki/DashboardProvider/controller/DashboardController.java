package com.edu.xmu.haruki.DashboardProvider.controller;

import com.edu.xmu.haruki.DashboardProvider.model.ResultMsg;
import com.edu.xmu.haruki.DashboardProvider.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @author haruki_9
 * @date 2022/7/17
 */
@RestController
@RequestMapping(value = "/dashboard",produces = "application/json;charset=utf-8")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;


    /**
     * 获取某房间的实时数据情况
     * 包括温度，湿度，蜂鸣器，火焰情况，继电器情况
     *
     * @param envId
     * @return
     */
    @GetMapping("/{envId}/situation")
    public Object realTimeRoomSituation(@PathVariable Integer envId){
        return dashboardService.realTimeEnvSituation(envId);
    }


    /**
     * 获取给定时间段，对应房间的温度情况
     *
     * @param envId
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/{envId}/temperature")
    public ResultMsg weeklyTemperature(@PathVariable Integer envId,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        return dashboardService.weeklyTemperature(envId,startDate,endDate);
    }

    /**
     * 获取时间段内的湿度情况
     *
     * @param envId
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/{envId}/humidity")
    public ResultMsg weeklyHumidity(@PathVariable Integer envId,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        return dashboardService.humidityStatistic(envId,startDate,endDate);
    }


    /**
     * 获取近一周时间异常数据统计情况，用饼图展示
     *
     * @param envId
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/{envId}/exception/statistic")
    public ResultMsg exceptionStatistic(@PathVariable Integer envId,
                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
        return dashboardService.exceptionStatistic(envId,startDate,endDate);
    }


    /**
     * 获取近一周全部的异常数据
     *
     * @param envId
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/{envId}/record/exception")
    public ResultMsg exceptionRecords(@PathVariable Integer envId,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
        return dashboardService.exceptionRecords(envId,startDate,endDate);
    }
}
