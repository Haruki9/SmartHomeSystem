package com.edu.xmu.haruki.gateway.controller;

import com.edu.xmu.haruki.gateway.model.ResultMsg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * @author haruki_9
 * @date 2022/7/21
 */
@RestController
@RequestMapping(value = "/fallback",produces = "application/json;charset=utf-8")
public class FallbackController {
    private final Logger log= Logger.getLogger(FallbackController.class.getName());

    @RequestMapping("/message")
    public ResultMsg fallback(){
        log.info("熔断处理！！！");
        return new ResultMsg(500,"Server Error.","微服务不可用！触发服务降级！" );
    }
}
