package com.edu.xmu.haruki.other.service;

import com.edu.xmu.haruki.other.dao.UserDao;
import com.edu.xmu.haruki.other.model.ResultMsg;
import com.edu.xmu.haruki.other.model.User;
import com.edu.xmu.haruki.other.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haruki_9
 * @date 2022/7/13
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    JwtUtil jwtUtil;

    public ResultMsg doLogin(String username, String password, HttpServletResponse response){
        User user=userDao.findUser(username);
        if (user==null){
            return new ResultMsg(404,"用户不存在！",null);
        }
        if (!user.getPassword().equals(password)){
            return new ResultMsg(403,"用户或密码错误！",null);
        }
        String token;
        try{
            Map<String, Object> claims=new HashMap<>();
            claims.put("username",username);
            claims.put("admin",user.getAdmin());
            token=jwtUtil.createToken(claims);
        }catch (Exception e){
            e.printStackTrace();
            token=null;
        }
        ResultMsg msg= new ResultMsg();
        if (token==null){
            msg.setCode(500);
            msg.setMsg("服务器认证错误！");
            response.setStatus(500);
            return msg;
        }
        Map<String,Object> data=new HashMap<>();
        data.put("username",user.getUsername());
        data.put("admin",user.getAdmin());
        data.put("AccessToken",token);
        response.setHeader("AccessToken", token);
        response.addCookie(new Cookie("AccessToken",token));
        response.addCookie(new Cookie("SameSite","None"));
        msg.setCode(200);
        msg.setMsg("登录成功！");
        msg.setData(data);
        return msg;
    }

    public ResultMsg findAllUsers(int page, int pageSize){
        Map<String, Object> res=new HashMap<>();
        int total=userDao.totalUserNum();
        List<User> users=userDao.getAllUsers(page*pageSize,pageSize);
        res.put("total",total);
        res.put("users",users);
        return new ResultMsg(200,"查询成功！",res);
    }

    public ResultMsg updateUser(Integer uId, User newUser) {
        User user=userDao.findUser(uId);
        if (user==null)return new ResultMsg(400,"用户不存在",null);
        newUser.setId(uId);
        if (userDao.updateUser(newUser)<=0)return new ResultMsg(500,"服务器更新失败！",null);
        return new ResultMsg(200,"用户更新成功！",null);
    }

    public ResultMsg updateUserStatus(Integer uId, Integer status){
        User user=userDao.findUser(uId);
        if (user==null)return new ResultMsg(400,"用户不存在",null);
        if (userDao.updateUserStatus(uId,status)<=0)return new ResultMsg(500,"用户状态更新失败！",null);
        return new ResultMsg(200,"用户状态更新成功！",null);
    }

    @Transactional
    public ResultMsg createUser(User user){
        User newUser;
        try{
            User target=userDao.findUser(user.getUsername());
            if (target!=null)return new ResultMsg(400,"用户已存在！",target);
            if (userDao.insertUser(user)<=0)return new ResultMsg(500,"新增用户失败！",null);
            newUser=userDao.findUser(user.getUsername());
        }catch (Exception e){
            e.printStackTrace();
            newUser=null;
        }
        if (newUser==null)
            return new ResultMsg(500,"服务器内部错误！",null);
        return new ResultMsg(200,"用户新增成功！",newUser);
    }

    public ResultMsg deleteUser(Integer uId) {
        User user=userDao.findUser(uId);
        if (user==null)return new ResultMsg(400,"用户不存在",null);
        if (userDao.delete(uId)<=0)return new ResultMsg(500,"服务器删除失败！",null);
        return new ResultMsg(200,"用户删除成功！",null);
    }
}
