package com.edu.xmu.haruki.other.controller;

import com.edu.xmu.haruki.other.model.ResultMsg;
import com.edu.xmu.haruki.other.model.User;
import com.edu.xmu.haruki.other.service.UserService;
import com.edu.xmu.haruki.other.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author haruki_9
 * @date 2022/7/13
 */
@RestController
@RequestMapping(produces = "application/json;charset=utf-8")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Object userLogin(@RequestParam String username, @RequestParam String password, HttpServletResponse response){
        ResultMsg msg=new ResultMsg();
        if (username==null||password==null){
            msg.setCode(400);
            msg.setMsg("用户名或密码不能为空");
            response.setStatus(400);
            return msg;
        }
        msg=userService.doLogin(username,password,response);
        return msg;
    }

    @GetMapping("/user/hello")
    public ResultMsg sayHello(HttpServletRequest request){
        String token=request.getHeader("AccessToken");
        String uname= (String) jwtUtil.getTargetClaimsFromToken(token,"username");
        Boolean admin=(Boolean) jwtUtil.getTargetClaimsFromToken(token,"admin");
        String hello=String.format("hello %s(%s)!",uname,admin?"admin":"user");
        return new ResultMsg(200,hello,null);
    }


    @GetMapping("/user/all")
    public Object getAllUser(@RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        if (page==null)page=1;
        if (pageSize==null)pageSize=10;
        return userService.findAllUsers(page,pageSize);
    }

    @PostMapping("/user/{uId}/update")
    public Object updateUser(@PathVariable Integer uId,@RequestBody User user){
        return userService.updateUser(uId,user);
    }

    @PostMapping("/user/create")
    public Object createUser(@RequestBody(required = false) User user){
        ResultMsg msg;
        if (user==null)msg=new ResultMsg(400,"缺失信息",null);
        msg=userService.createUser(user);
        return msg;
    }

    @PostMapping("/user/{uId}/delete")
    public Object deleteUser(@PathVariable Integer uId){
        return userService.deleteUser(uId);
    }

    @PostMapping("/user/{uId}/ban")
    public ResultMsg banUser(@PathVariable Integer uId){
        return userService.updateUserStatus(uId,0);
    }

    @PostMapping("/user/{uId}/activate")
    public ResultMsg activateUser(@PathVariable Integer uId){
        return userService.updateUserStatus(uId,1);
    }
}
