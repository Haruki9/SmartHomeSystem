package com.edu.xmu.haruki.environmentService.mapper;

import com.edu.xmu.haruki.environmentService.model.record.Record;
import com.edu.xmu.haruki.environmentService.model.record.RecordVo;
import com.edu.xmu.haruki.environmentService.model.sensor.SensorType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author haruki_9
 * @date 2022/7/14
 */
@Mapper
public interface RecordsMapper {
    List<Record> getAllRecords(RecordVo recordVo, int start, int size);

    List<Record> getRecordsBySensorId(int sensorId,int start,int size);

    List<Record> getRecordsByEnvId(int envId,int start,int size);

    List<Record> getExceptionRecords(RecordVo recordVo, int start, int size);

    int insertRecords(List<Record> records);

    int insertRecord(Record record);

    int deleteRecord(RecordVo recordVo);

    int deleteRecordById(int recordId);

    int deleteRecordsById(int[] idList);

    Record getRecordByEnvIdAndType(int envId, SensorType type);

    int insertExceptionRecord(Record record);
}
