package com.weibo.user;

import com.weibo.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        // 测试Spring上下文是否加载成功
        System.out.println("Spring上下文加载成功");
    }
}