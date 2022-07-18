package com.edu.xmu.haruki.environmentService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author haruki_9
 * @date 2022/7/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMsg {
    private Integer code;
    private String msg;
    private Object data;
}
