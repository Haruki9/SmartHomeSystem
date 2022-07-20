package com.edu.xmu.haruki.environmentService.service;

import com.edu.xmu.haruki.environmentService.mapper.EnvironmentMapper;
import com.edu.xmu.haruki.environmentService.mapper.SensorMapper;
import com.edu.xmu.haruki.environmentService.model.ResultMsg;
import com.edu.xmu.haruki.environmentService.model.environment.BasicEnvironment;
import com.edu.xmu.haruki.environmentService.model.sensor.BasicSensor;
import com.edu.xmu.haruki.environmentService.model.sensor.SensorType;
import com.edu.xmu.haruki.environmentService.model.sensor.SensorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author haruki_9
 * @date 2022/7/14
 */
@Service
public class SensorService {
    @Autowired
    private SensorMapper sensorMapper;

    @Autowired
    private EnvironmentMapper environmentMapper;

    public List<BasicSensor> getAllSensors(int page,int pageSize){
        List<BasicSensor> res;
        try{
            res=sensorMapper.getAllSensors(page*pageSize,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            res=null;
        }

        return res;
    }

    public List<BasicSensor> retrieveSensor(SensorVo sensorVo,int page,int pageSize){
        List<BasicSensor> res;
        try{
            res=sensorMapper.getSensor(sensorVo,page*pageSize,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            res=null;
        }
        return res;
    }


    @Transactional
    public ResultMsg createNewSensor(BasicSensor sensor){
        ResultMsg res=new ResultMsg();
        try{
            BasicSensor exist=sensorMapper.getSensorByEnvIdName(sensor.getEnvBelongs(),sensor.getName());
            if (exist!=null){
                res.setCode(400);
                res.setMsg("目标传感器已存在！");
                return res;
            }
            sensorMapper.insertSensor(sensor);
        }catch (Exception e){
            res.setCode(500);
            res.setMsg("新建传感器失败！");
            return res;
        }
        res.setCode(200);
        res.setMsg("新建传感器成功！");
        res.setData(sensorMapper.getSensorByEnvIdName(sensor.getEnvBelongs(),sensor.getName()));
        return res;
    }


    @Transactional
    public ResultMsg updateSensor(Integer sensorId,BasicSensor sensor){
        ResultMsg res=new ResultMsg();
        sensor.setId(sensorId);
        int updateTag;
        try{
            updateTag= sensorMapper.updateSensor(sensor);
        }catch (Exception e){
            e.printStackTrace();
            res.setCode(500);
            res.setMsg("传感器更新失败！内部服务出错");
            return res;
        }
        if (updateTag==0){
            res.setCode(404);
            res.setMsg("目标传感器不存在！");
            return res;
        }
        res.setCode(200);
        res.setMsg("传感器更新成功！");
        res.setData(sensorMapper.getSensorById(sensorId));
        return res;
    }

    public ResultMsg updateSensorStatus(Integer sensorId,Integer status){
        ResultMsg res=new ResultMsg();


        BasicSensor target;
        try{
            target=sensorMapper.getSensorById(sensorId);
        }catch (Exception e){
            e.printStackTrace();
            target=null;
        }
        if (target==null){
            res.setCode(404);
            res.setMsg("未找到目标传感器！");
            return res;
        }
        try{
            BasicEnvironment env=environmentMapper.getEnvironmentById(target.getEnvBelongs());
            target.setStatus(status);
            sensorMapper.updateSensor(target);
            if (env==null||env.getStatus()<=0){
                res.setCode(401);
                res.setMsg("场景不可用，无法激活！");
                return res;
            }
        }catch (Exception e){
            e.printStackTrace();
            res.setCode(500);
            res.setMsg("传感器更新失败！");
            return res;
        }
        res.setCode(200);
        res.setMsg("传感器更新成功！");
        res.setData(sensorMapper.getSensorById(sensorId));
        return res;
    }
}
