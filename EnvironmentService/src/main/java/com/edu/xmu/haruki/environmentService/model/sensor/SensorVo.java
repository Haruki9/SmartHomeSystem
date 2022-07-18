package com.edu.xmu.haruki.environmentService.model.sensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author haruki_9
 * @date 2022/7/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorVo {
    private Integer id;
    private String name;
    private SensorType type;
    private Integer envBelongs;     //所属环境场景ID
    private Integer status;      // 传感器状态：0禁用，1启用，-1删除
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
