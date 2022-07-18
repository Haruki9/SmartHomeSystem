package com.edu.xmu.haruki.environmentService.mapper;

import com.edu.xmu.haruki.environmentService.model.sensor.BasicSensor;
import com.edu.xmu.haruki.environmentService.model.sensor.SensorVo;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author haruki_9
 * @date 2022/7/14
 */
@Mapper
public interface SensorMapper {

    BasicSensor getSensorById(int sensorId);

    List<BasicSensor> getAllSensors(int start,int size);

    List<BasicSensor> getSensor(SensorVo sensorVo, int start, int size);

    List<BasicSensor> getSensorByEnvId(int envId);

    int insertSensor(BasicSensor sensor);

    int updateSensor(BasicSensor sensor);

    BasicSensor getSensorByEnvIdName(int envBelongs, String name);
}
