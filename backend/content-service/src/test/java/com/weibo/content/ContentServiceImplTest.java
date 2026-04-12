package com.weibo.content;

import com.weibo.content.service.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContentServiceImplTest {

    @Autowired
    private ContentService contentService;

    @Test
    void contextLoads() {
        // 测试Spring上下文是否加载成功
        System.out.println("内容微服务Spring上下文加载成功");
    }
}