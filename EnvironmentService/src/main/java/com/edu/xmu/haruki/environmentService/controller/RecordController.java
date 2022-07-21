package com.edu.xmu.haruki.environmentService.controller;

import com.edu.xmu.haruki.environmentService.model.ResultMsg;
import com.edu.xmu.haruki.environmentService.model.record.Record;
import com.edu.xmu.haruki.environmentService.model.record.RecordVo;
import com.edu.xmu.haruki.environmentService.model.sensor.SensorType;
import com.edu.xmu.haruki.environmentService.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author haruki_9
 * @date 2022/7/14
 */
@RestController
@RequestMapping(value = "/record",produces = "application/json;charset=utf-8")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping("/all")
    public ResultMsg getAllRecords(@RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "10") Integer pageSize){
        ResultMsg msg=new ResultMsg();
        List<Record> records=recordService.getAllRecords(null,page,pageSize);
        msg.setCode(200);
        msg.setMsg("记录查询成功！");
        msg.setData(records);
        return msg;
    }

    @GetMapping("/retrieve")
    public ResultMsg retrieveRecords(@RequestParam(required = false) Long id, @RequestParam(required = false) Integer sensorId,
                                     @RequestParam(required = false) Integer envId, @RequestParam(required = false) SensorType type,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
                                     @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer pageSize){
        ResultMsg msg=new ResultMsg();
        RecordVo recordVo=new RecordVo();
        recordVo.setId(id);recordVo.setEnvId(envId);recordVo.setSensorId(sensorId);recordVo.setSensorType(type);
        recordVo.setStartTime(startTime);recordVo.setEndTime(endTime);
        List<Record> records=recordService.getAllRecords(recordVo,page,pageSize);
        msg.setCode(200);
        msg.setMsg("查询成功！");
        msg.setData(records);
        return msg;
    }

    @GetMapping("/exception/retrieve")
    public ResultMsg retrieveExceptionRecords(@RequestParam(required = false) Long id, @RequestParam(required = false) Integer sensorId,
                                              @RequestParam(required = false) Integer envId,@RequestParam(required = false) SensorType type,
                                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
                                              @RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "20") Integer pageSize){
        ResultMsg msg=new ResultMsg();
        RecordVo recordVo=new RecordVo();
        recordVo.setId(id);recordVo.setEnvId(envId);recordVo.setSensorId(sensorId);recordVo.setSensorType(type);
        recordVo.setStartTime(startTime);recordVo.setEndTime(endTime);
        List<Record> records=recordService.getExceptionRecords(recordVo,page,pageSize);
        msg.setCode(200);
        msg.setMsg("查询成功！");
        msg.setData(records);
        return msg;
    }


    @PostMapping("/insert")
    public ResultMsg insertRecord(@RequestBody Record record){
        return recordService.insertRecord(record);
    }

    @PostMapping("/exception/insert")
    public ResultMsg insertExceptionRecord(@RequestBody Record record){
        return recordService.insertExceptionRecord(record);
    }

    @PostMapping("/mulInsert")
    public ResultMsg multipleInsertRecords(@RequestBody List<Record> records){
        return recordService.insertRecords(records);
    }

    @PostMapping("/delete")
    public ResultMsg deleteRecords(@RequestBody RecordVo recordVo){
        return recordService.deleteRecord(recordVo);
    }

    @PostMapping("/{rId}/delete")
    public ResultMsg deleteRecord(@PathVariable Integer rId){
        return recordService.deleteRecordById(rId);
    }

    @PostMapping("/mulDelete")
    public ResultMsg deleteRecords(@RequestParam int[] ids){
        return recordService.deleteRecordsById(ids);
    }

}
