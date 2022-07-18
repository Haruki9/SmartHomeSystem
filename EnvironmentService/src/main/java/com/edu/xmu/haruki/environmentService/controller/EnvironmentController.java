package com.edu.xmu.haruki.environmentService.controller;

import com.edu.xmu.haruki.environmentService.model.ResultMsg;
import com.edu.xmu.haruki.environmentService.model.environment.BasicEnvironment;
import com.edu.xmu.haruki.environmentService.service.EnvironmentService;
import com.edu.xmu.haruki.environmentService.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haruki_9
 * @date 2022/7/14
 */
@RestController
@RequestMapping(value = "/environment",produces = "application/json;charset=utf-8")
public class EnvironmentController {

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private RecordService recordService;

    @GetMapping("/all")
    public ResultMsg getAllEnvironment(){
        ResultMsg msg=new ResultMsg();
        Map<String,Object> data=new HashMap<>();
        data.put("total",environmentService.totalEnvNum());
        data.put("environments",environmentService.getAllEnvironments());
        msg.setData(data);
        msg.setCode(200);
        msg.setMsg("查询成功！");
        return msg;
    }

    @GetMapping("/available")
    public ResultMsg allAvailableEnvironment(){
        ResultMsg msg=new ResultMsg();
        Map<String,Object> data=new HashMap<>();
        data.put("environments",environmentService.getAllEnvironments());
        msg.setData(data);
        msg.setCode(200);
        msg.setMsg("查询成功！");
        return msg;
    }

    @GetMapping("/{envId}/info")
    public ResultMsg getEnvironmentById(@PathVariable Integer envId){
        ResultMsg msg=new ResultMsg();
        msg.setCode(200);
        msg.setMsg("查询成功");
        BasicEnvironment env=environmentService.retrieveEnvironmentById(envId);
        if (env==null)msg.setMsg("查询结果为空！");
        msg.setData(env);
        return msg;
    }

    /**
     * 返回实时的房间情况数据
     *
     * @param envId
     * @return
     */
    @GetMapping("/{envId}/realtime")
    public ResultMsg envRealTimeSituation(@PathVariable Integer envId){
        ResultMsg msg=new ResultMsg();




        msg.setCode(200);
        msg.setMsg("查询成功!");
        msg.setData(environmentService.envRealTimeSituation(envId));
        return msg;
    }

    @PostMapping("/insert")
    public ResultMsg createNewEnvironment(@RequestBody BasicEnvironment environment){
        return environmentService.createNewEnvironment(environment);
    }

    @PostMapping("/{envId}/delete")
    public ResultMsg deleteEnvironment(@PathVariable Integer envId){
        return environmentService.banOrDeleteOrActivateEnvironment(envId,-1);
    }

    @PostMapping("/{envId}/ban")
    public ResultMsg banEnvironment(@PathVariable Integer envId){
        return environmentService.banOrDeleteOrActivateEnvironment(envId,0);
    }

    @PostMapping("/{envId}/activate")
    public ResultMsg activateEnvironment(@PathVariable Integer envId){
        return environmentService.banOrDeleteOrActivateEnvironment(envId,1);
    }
}
