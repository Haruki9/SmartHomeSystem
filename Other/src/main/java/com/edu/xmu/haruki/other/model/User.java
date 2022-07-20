package com.edu.xmu.haruki.other.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author haruki_9
 * @date 2022/7/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer userStatus;
    private Boolean admin;
    private LocalDate createTime;
    private LocalDate updateTime;
}
