package com.edu.xmu.haruki.environmentService.controller;

import com.edu.xmu.haruki.environmentService.model.ResultMsg;
import com.edu.xmu.haruki.environmentService.model.sensor.BasicSensor;
import com.edu.xmu.haruki.environmentService.model.sensor.SensorType;
import com.edu.xmu.haruki.environmentService.model.sensor.SensorVo;
import com.edu.xmu.haruki.environmentService.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haruki_9
 * @date 2022/7/14
 */
@RestController
@RequestMapping(value = "/sensor",produces = "application/json;charset=utf-8")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @GetMapping("/type/all")
    public ResultMsg getAllSensorType(){
        ResultMsg msg=new ResultMsg();
        Map<String,Object> map=new HashMap<>();
        map.put("SensorType",SensorType.values());
        msg.setCode(200);
        msg.setMsg("查询成功");
        msg.setData(map);
        return msg;
    }


    @GetMapping("/all")
    public ResultMsg getAllSensors(@RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "10") Integer pageSize){
        ResultMsg msg=new ResultMsg();
        List<BasicSensor> sensors=sensorService.getAllSensors(page,pageSize);
        msg.setMsg("查询成功");
        msg.setCode(200);
        msg.setData(sensors);
        return msg;
    }

    @GetMapping("/retrieve")
    public ResultMsg getSensors(@RequestParam(required = false) String name, @RequestParam(required = false) Integer envId,
                                @RequestParam(required = false) SensorType type, @RequestParam(required = false) Integer status,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss") LocalDateTime startTime,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss") LocalDateTime endTime,
                                @RequestParam(defaultValue = "0")Integer page, @RequestParam(defaultValue = "10") Integer pageSize){
        ResultMsg msg=new ResultMsg();
        SensorVo sensorVo=new SensorVo(null,name,type,envId,status,startTime,endTime);
        List<BasicSensor> sensors=sensorService.retrieveSensor(sensorVo,page,pageSize);
        msg.setMsg("查询成功");
        msg.setCode(200);
        msg.setData(sensors);
        return msg;
    }

    @PostMapping("/{sensorId}/update")
    public ResultMsg updateSensor(@PathVariable Integer sensorId,@RequestBody BasicSensor sensor){
        return sensorService.updateSensor(sensorId,sensor);
    }

    @PostMapping("/insert")
    public ResultMsg createNewSensor(@RequestBody BasicSensor sensor){
        return sensorService.createNewSensor(sensor);
    }

    @PostMapping("/{sensorId}/ban")
    public ResultMsg banSensor(@PathVariable Integer sensorId){
        return sensorService.updateSensorStatus(sensorId,0);
    }

    @PostMapping("/{sensorId}/activate")
    public ResultMsg activateSensor(@PathVariable Integer sensorId){
        return sensorService.updateSensorStatus(sensorId,1);
    }
}
