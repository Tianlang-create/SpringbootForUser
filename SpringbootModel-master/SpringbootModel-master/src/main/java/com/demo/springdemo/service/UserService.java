package com.demo.springdemo.service;

import com.demo.springdemo.enerity.User;
import com.demo.springdemo.enerity.dto.UserDto;
import com.demo.springdemo.DAO.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  //配置Spring的bean
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public User add(UserDto user) {//修改类型
        User userPojo = new User();
        BeanUtils.copyProperties(user, userPojo);
        return userRepository.save(userPojo);
        //调用数据访问类方法
    }

    @Override
    public User select(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> {
            throw new IllegalArgumentException("用户不存在!");
        });
        return null;
    }

    @Override
    public User edit(UserDto user) {
        User userPojo = new User();
        BeanUtils.copyProperties(user, userPojo);
        return userRepository.save(userPojo);
    }

    @Override
    public void delete(Integer userId) {
        userRepository.deleteById(userId);
    }
}
