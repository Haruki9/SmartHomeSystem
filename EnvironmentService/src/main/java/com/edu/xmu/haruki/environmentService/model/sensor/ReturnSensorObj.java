package com.edu.xmu.haruki.environmentService.model.sensor;

import lombok.Data;

import java.util.List;

/**
 * @author haruki_9
 * @date 2022/7/14
 */
@Data
public class ReturnSensorObj {
    private Integer total;
    private Integer page;
    private Integer pageSize;
    private List<BasicSensor> sensors;
}
