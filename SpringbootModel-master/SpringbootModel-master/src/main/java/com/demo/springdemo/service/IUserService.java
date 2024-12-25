package com.demo.springdemo.service;

import com.demo.springdemo.enerity.User;
import com.demo.springdemo.enerity.dto.UserDto;


public interface IUserService {
    /*
    *
    * 增加用户
     */
    User add(UserDto user);

    /**
     *  查询用户
     * @param userId
     * @return
     */
    User select(Integer userId);

    /**
     * 修改用户
     * @param user 修改用户对象
     * @return
     */
    User edit(UserDto user);

    /**
     * 删除用户
     * @param userId
     */
    void delete(Integer userId);
}
