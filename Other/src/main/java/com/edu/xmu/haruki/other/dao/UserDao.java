package com.edu.xmu.haruki.other.dao;

import com.edu.xmu.haruki.other.mapper.UserMapper;
import com.edu.xmu.haruki.other.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author haruki_9
 * @date 2022/7/13
 */
@Repository
public class UserDao {

    @Autowired
    UserMapper userMapper;

    public int totalUserNum(){
        return userMapper.totalUserNum();
    }

    public User findUser(String username){
        return userMapper.getUserByUsername(username);
    }

    public User findUser(String username,String password){
        return userMapper.getUserByUnamePasswd(username,password);
    }

    public User findUser(Integer uId){
        return userMapper.getUserByUId(uId);
    }

    public List<User> getAllUsers(int start, int size){
        return userMapper.getAllUsers(start,size);
    }

    public int insertUser(User user){
        return userMapper.createUser(user);
    }

    public int updateUser(User user){
        return userMapper.updateUser(user);
    }

    public int updateUserStatus(Integer uId,int status){
        return userMapper.updateUserStatus(uId,status);
    }

    public int delete(Integer uId) {
        return userMapper.deleteUser(uId);
    }
}
