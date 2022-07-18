package com.edu.xmu.haruki.environmentService.service;

import com.edu.xmu.haruki.environmentService.mapper.EnvironmentMapper;
import com.edu.xmu.haruki.environmentService.model.ResultMsg;
import com.edu.xmu.haruki.environmentService.model.environment.BasicEnvironment;
import com.edu.xmu.haruki.environmentService.model.record.Record;
import com.edu.xmu.haruki.environmentService.model.sensor.BasicSensor;
import com.edu.xmu.haruki.environmentService.model.sensor.SensorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author haruki_9
 * @date 2022/7/14
 */
@Service
public class EnvironmentService {
    @Autowired
    private SensorService sensorService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private EnvironmentMapper environmentMapper;


    public Integer totalEnvNum(){
        Integer num;
        try{
            num=environmentMapper.totalNum();
        }catch (Exception e){
            e.printStackTrace();
            num=null;
        }
        return num;
    }

    public Object envRealTimeSituation(Integer envId){
        HashMap<String, Record> res=new HashMap<>();
        res.put("temperature", recordService.getRecordByEnvIdAndType(envId, SensorType.TEMPERATURE));
        res.put("humidity",recordService.getRecordByEnvIdAndType(envId, SensorType.HUMIDITY));
        res.put("fire",recordService.getRecordByEnvIdAndType(envId,SensorType.FIRE_FLAME));
        res.put("buzzer",recordService.getRecordByEnvIdAndType(envId,SensorType.BUZZER));
        return res;
    }

    public List<BasicEnvironment> allAvailableEnvs(){
        return environmentMapper.getAllAvailableEnvs();
    }

    public List<BasicEnvironment> getAllEnvironments(){
        List<BasicEnvironment> environments;
        try{
            environments=environmentMapper.getAllEnvironment();
        }catch (Exception e){
            e.printStackTrace();
            environments=null;
        }
        return environments;
    }

    public List<BasicEnvironment> retrieveEnvironmentByName(String name){
        List<BasicEnvironment> environments;
        try{
            environments=environmentMapper.getEnvironmentByName(name);
        }catch (Exception e){
            e.printStackTrace();
            environments=null;
        }
        return environments;
    }

    public BasicEnvironment retrieveEnvironmentById(Integer envId){
        BasicEnvironment environment;
        try{
            environment=environmentMapper.getEnvironmentById(envId);
        }catch (Exception e){
            e.printStackTrace();
            environment=null;
        }
        return environment;
    }

    @Transactional
    public ResultMsg createNewEnvironment(BasicEnvironment environment){
        ResultMsg msg=new ResultMsg();
        if (environment==null){
            msg.setCode(400);
            msg.setMsg("场景信息缺失！");
            return msg;
        }
        int res;
        try{
            res=environmentMapper.insertEnvironment(environment);
        }catch (Exception e){
            e.printStackTrace();
            res=0;
        }
        if (res<=0){
            msg.setCode(500);
            msg.setMsg("新建场景失败！");
            return msg;
        }
        environment=environmentMapper.getEnvironmentByName(environment.getName()).get(0);
        msg.setCode(200);
        msg.setMsg("新建场景成功");
        msg.setData(environment);
        return msg;
    }

    @Transactional
    public ResultMsg banOrDeleteOrActivateEnvironment(Integer envId, Integer status){
        ResultMsg resultMsg=new ResultMsg();
        int envBanTag;
        try {
            envBanTag= environmentMapper.updateEnvironmentStatus(envId,status);
        }catch (Exception e){
            e.printStackTrace();
            resultMsg.setCode(500);
            resultMsg.setMsg("场景状态更新失败");
            return resultMsg;
        }
        if (envBanTag==0){
            resultMsg.setCode(404);
            resultMsg.setMsg("目标场景不存在");
            return resultMsg;
        }
        resultMsg.setCode(200);
        resultMsg.setMsg("场景状态更新成功");
        resultMsg.setData(environmentMapper.getEnvironmentById(envId));
        return resultMsg;
    }
}
