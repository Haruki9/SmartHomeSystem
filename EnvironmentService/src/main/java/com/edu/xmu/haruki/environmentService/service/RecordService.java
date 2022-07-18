package com.edu.xmu.haruki.environmentService.service;

import com.edu.xmu.haruki.environmentService.mapper.RecordsMapper;
import com.edu.xmu.haruki.environmentService.model.ResultMsg;
import com.edu.xmu.haruki.environmentService.model.record.Record;
import com.edu.xmu.haruki.environmentService.model.record.RecordVo;
import com.edu.xmu.haruki.environmentService.model.sensor.SensorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author haruki_9
 * @date 2022/7/14
 */
@Service
public class RecordService {
    @Autowired
    private RecordsMapper recordsMapper;


    public List<Record> getAllRecords(RecordVo recordVo,Integer page,Integer pageSize){
        List<Record> records;
        try{
            records=recordsMapper.getAllRecords(recordVo,page*pageSize,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            records=null;
        }
        return records;
    }

    public Record getRecordByEnvIdAndType(Integer envId, SensorType type){
        return recordsMapper.getRecordByEnvIdAndType(envId,type);
    }


    public List<Record> getRecordBySensorId(Integer sensorId,Integer page,Integer pageSize){
        List<Record> records;
        try{
            records=recordsMapper.getRecordsBySensorId(sensorId,page*pageSize,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            records=null;
        }
        return records;
    }

    public List<Record> getRecordByEnvId(Integer envId,Integer page, Integer pageSize){
        List<Record> records;
        try{
            records=recordsMapper.getRecordsByEnvId(envId,page*pageSize,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            records=null;
        }
        return records;
    }

    @Transactional
    public ResultMsg insertRecord(Record record){
        int res;
        try{
            res= recordsMapper.insertRecord(record);
        }catch (Exception e){
            e.printStackTrace();
            res=0;
        }
        ResultMsg resultMsg=new ResultMsg();
        if (res<=0){
            resultMsg.setCode(500);
            resultMsg.setMsg("传感器数据传入失败！");
            return resultMsg;
        }
        resultMsg.setCode(200);
        resultMsg.setMsg("传感器数据传入成功！");
        return resultMsg;
    }

    @Transactional
    public ResultMsg insertRecords(List<Record> records){
        int res;
        try{
            res= recordsMapper.insertRecords(records);
        }catch (Exception e){
            e.printStackTrace();
            res=0;
        }
        ResultMsg resultMsg=new ResultMsg();
        if (res<=0){
            resultMsg.setCode(500);
            resultMsg.setMsg("批量传感器数据传入失败！");
            return resultMsg;
        }
        resultMsg.setCode(200);
        resultMsg.setMsg("批量传感器数据传入成功！");
        return resultMsg;
    }

    @Transactional
    public ResultMsg deleteRecord(RecordVo recordVo){
        ResultMsg msg=new ResultMsg();
        int res;
        try{
            res= recordsMapper.deleteRecord(recordVo);
        }catch (Exception e){
            e.printStackTrace();
            res=0;
        }
        if (res<=0){
            msg.setCode(500);
            msg.setMsg("数据删除失败！");
            return msg;
        }
        msg.setCode(200);
        msg.setMsg("数据删除成功！");
        return msg;
    }

    @Transactional
    public ResultMsg deleteRecordById(Integer id){
        ResultMsg msg=new ResultMsg();
        int res;
        try{
            res= recordsMapper.deleteRecordById(id);
        }catch (Exception e){
            e.printStackTrace();
            msg.setCode(500);
            msg.setMsg("数据删除失败！");
            return msg;
        }
        if (res==0){
            msg.setCode(404);
            msg.setMsg("目标数据未找到！");
            return msg;
        }
        msg.setCode(200);
        msg.setMsg("数据删除成功！");
        return msg;
    }

    @Transactional
    public ResultMsg deleteRecordsById(int[] idList){
        ResultMsg msg=new ResultMsg();

        int res;
        try{
            res= recordsMapper.deleteRecordsById(idList);
        }catch (Exception e){
            e.printStackTrace();
            res=0;
        }
        if (res<=0){
            msg.setCode(500);
            msg.setMsg("数据删除失败！");
            return msg;
        }
        msg.setCode(200);
        msg.setMsg("数据删除成功！");
        return msg;
    }

    public List<Record> getExceptionRecords(RecordVo recordVo, Integer page, Integer pageSize) {
        return recordsMapper.getExceptionRecords(recordVo,page*pageSize,pageSize);
    }
}
