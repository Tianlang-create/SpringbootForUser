## Springboot的用户增删查改方法（三层）

*<u>Made by Tianlang  Welcome to visit https://github.com/Tianlang-create</u>*



### 背景

​		左家垅大学安排计算机专业的学生在期末前进行了JavaWeb的实训，Tianlang对此很疑惑：

​		1>什么是数据库？怎么使用  2>怎么在IDEA连接数据库 3>怎么搭建应用DSC框架 特别地，Dao层（或Repository）怎么和数据库搭上关系？Service层又怎么实现调用Dao接口？Controller层和之前做的应用选择块有什么联系？

​		Xushu老师笑着说：针对概念问题，可以关注我的牛客<u>（或添加QQ2090256385）</u>

​		**具体方法如下：**

### 连接数据库问题

​		在src/main/resources/application.properties里添加如下代码

```properties
server.port=8088
spring.datasource.url=jdbc:mysql://localhost:3306/springdemo
spring.datasource.username=root
spring.datasource.password=#你的数据库密码
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

​		这些代码分别是什么意思呢？ 1行是我们设置的端口号，2-6行的开头都是**spring**框架内容

`jdbc:mysql`:**数据库驱动类型（MySQL）**`://localhost`:**数据库服务器地址（本地主机）**

`:3306`:**数据库端口号（MySQL默认端口）**  `/springdemo`:**数据库名称**

`spring.datasource.driver-class-name`**是数据源的驱动类名**

`com.mysql.cj.jdbc.Driver`**是MySQL数据库的驱动类名（用于Java 8及以上版本）**

​		特别地，倘若我们要在终端实现SQL语句的输出，可以再添加:

```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```



### Dao层实现和数据库交互后，Service层怎么“通知”Dao我要使用？

### **1.Dao与数据库的“交互”**

​			在目录页新建了Dao软件包后，话不多说，我们直接拍上代码：

```Java
package com.demo.springdemo.Dao;

import com.demo.springdemo.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository//spring的bean 细化了逻辑块
public interface UserRepository extends CrudRepository<User, Integer> {//继承CrudRepository
}

//参考路径:com/demo/springdemo/Dao/UserRepository.java
```

​			怎么理解**Dao层实现和数据库交互**？我们其实通过Spring框架（之前博客写过），如在第七行写下注解@Repository**标记一个类或接口为数据访问层（DAO）的实现**

​			这其实就是表明`UserRepository`接口是数据访问层的一部分。

​			接下来继承`CrudRepository`接口之后，`UserRepository`接口可以提供基本的CRUD操作，并且可以根据需要添加自定义的方法来实现更复杂的数据访问逻辑

​			**肯定有同学会问：什么是接口，我该怎么使用接口？**

​			假设我们有一个动物园，里面有不同的动物。我们可以定义一个`Animal`接口，如下：

```java
javaCopyInsertpublic interface Animal {
    void sound();
}
```

​			**这个接口定义了一个`sound`方法，所有动物都应该能够发出声音。**

​			现在，我们可以实现这个接口的不同类，例如：

```java
javaCopyInsertpublic class Dog implements Animal {
    @Override
    public void sound() {
        System.out.println("汪汪！");
    }
}

public class Cat implements Animal {
    @Override
    public void sound() {
        System.out.println("喵喵！");
    }
}
```

​			这两个类都实现了`Animal`接口，但是它们的实现方式不同。

​			现在，我们可以使用这些类来实现动物园的功能，如下：

```java
javaCopyInsertAnimal dog = new Dog();
dog.sound(); // 汪汪！

Animal cat = new Cat();
cat.sound(); // 喵喵！
```

通过使用接口，我们可以实现不同类之间的解耦和扩展性。那么，进入了我们Service层后，其基本核心也是一样的。

​		其实，通过使用`@Autowired`注解，开发者可以不需要手动创建`UserRepository`的实现类，也不需要手动注入依赖的bean。Spring会自动完成这些工作。



### 2.Service层使用CRUD操作

​			在Spring框架中，`@Autowired`注解可以自动注入依赖的bean。由于`UserRepository`是接口，所以Spring会自动创建一个实现该接口的bean，并将其注入到当前类中

​			我们直接上代码：

```java
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

```

​			着重理解的是，我们不意味着在Service层只需要调用接口就行了，使用`@Service`注解来标记一个类，表示它是一个服务层组件。然后使用依赖注入将DAO接口注入到服务层组件中，以便在服务层中使用DAO的实现类来执行数据库操作

​			此时，我们看用户的增操作，这里的UserDto user，前一个是我们构建的用户实体类(内容个数，如ID，Email等)，user是我们在Controller接受的UserDto对象，其实，真正的增加操作只是userRepository.save(userPojo)，其上两行是类型转换为User,UserDto的类是User的限制情况（因为我这里是判定用户的错误输入情况以及网络攻击），简而言之，是以User形式存储在数据库中，存储方法我们在之前的Dao层通过扩展CRUD实现

​			相似的删除，改变，查询不再一一赘述了。

### Controller层--与网络的交流层

​			我们之前在Service层不是要传入一个User吗？源头就在这里！

​			我们直接放代码：

```java
@RestController // 接口方法返回值为json
@RequestMapping("/user")  // 前缀 localhost:8088/user
public class User_controller {

    @Autowired
    IUserService userService;//IUserService 接口的实例 userService 是通过 @Autowired 注解自动注入的。
    //REST
    //增加
    @PostMapping
    public String add(@Validated @RequestBody UserDto user){//提示传入文本
        User userNew= userService.add(user);
        return ResponseMessage.success(userNew).toString();
    }}
```

​				直接看add功能，@RequestBody UserDto user，代表了从JSON格式中自动提取user，userService.add是我们之前在Service中实现的add方法，正确返回User后发送信息



​				以上就是三层的实现流程，该实例的所有代码请查询开头的github账号，目前基本功能已经实现。江湖相逢缘是君，感谢观看！
