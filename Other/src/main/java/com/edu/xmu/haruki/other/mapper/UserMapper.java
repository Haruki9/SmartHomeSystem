package com.edu.xmu.haruki.other.mapper;

import com.edu.xmu.haruki.other.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author haruki_9
 * @date 2022/7/13
 */
@Mapper
public interface UserMapper {
    int totalUserNum();

    List<User> getAllUsers(int start, int size);

    User getUserByUId(int uId);

    User getUserByUsername(String username);
    int updateUser(User user);

    int updateUserStatus(Integer uId, int userStatus);

    int createUser(User user);

    int deleteUser(int uId);

    User getUserByUnamePasswd(String username, String password);
}
