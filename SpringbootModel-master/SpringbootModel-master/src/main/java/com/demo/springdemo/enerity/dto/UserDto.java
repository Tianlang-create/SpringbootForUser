package com.demo.springdemo.enerity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.ModelAttribute;

public class UserDto {

    @ModelAttribute
    public UserDto getUserDto(){
        return new UserDto();
    }
    private Integer UserId;
    @NotBlank(message = "用户名不能为空")
    private String userName;
    @Email(message = "格式不正确")
    private String email;
    @NotBlank(message = "密码不能为空")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", UserId=" + UserId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
