package com.demo.springdemo.controller;
import com.demo.springdemo.enerity.ResponseMessage;
import com.demo.springdemo.enerity.User;
import com.demo.springdemo.enerity.dto.UserDto;
import com.demo.springdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController // 接口方法返回值为json
@RequestMapping("/user")  // 前缀 localhost:8080/user
public class User_controller {

    @Autowired

    IUserService userService;
    //REST
    //增加
    @PostMapping
    public String add(@Validated @RequestBody UserDto user){//提示传入文本
        User userNew= userService.add(user);
        return ResponseMessage.success(userNew).toString();
    }
    //查询@GetMapping
    @GetMapping("/{UserId}")
    public ResponseMessage get(@PathVariable Integer UserId){
        User userNew=userService.select(UserId);
        return ResponseMessage.success(userNew);
    }
    //修改@PutMapping
    @PutMapping
    public ResponseMessage edit(@Validated @RequestBody UserDto user){
        User userNew=userService.edit(user);
        return ResponseMessage.success(userNew);
    }
    //删除@DeleteMapping
    @DeleteMapping("/{UserId}")
    public ResponseMessage delete(@PathVariable Integer UserId){
        userService.delete(UserId);
        return ResponseMessage.success(null);
    }

}
