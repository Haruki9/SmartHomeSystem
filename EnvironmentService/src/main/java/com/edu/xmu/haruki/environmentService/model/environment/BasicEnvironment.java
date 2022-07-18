package com.edu.xmu.haruki.environmentService.model.environment;

import lombok.Data;

/**
 * @author haruki_9
 * @date 2022/7/13
 */

@Data
public class BasicEnvironment {
    private Integer id;
    private String name;
    private String info;
    private Integer status;
}
