package com.demo.springdemo.DAO;

import com.demo.springdemo.enerity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository//spring的bean 细化了逻辑块
public interface UserRepository extends CrudRepository<User, Integer> {//继承CrudRepository

}
