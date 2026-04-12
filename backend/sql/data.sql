-- 广智分布式内容发布与互动平台 - 数据初始化脚本
-- 说明：此文件仅插入数据，不建表。请先执行建库脚本后再执行此文件

USE weibo_user;

-- 清空并插入用户数据
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE user_relation;
TRUNCATE TABLE user;

INSERT INTO `user` (`username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`) VALUES
('admin', '123456', '系统管理员', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', 'admin@guangzhi.com', '13800138000', 1, '系统管理员', 1),
('zhangsan', '123456', '张三', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 'zhangsan@guangzhi.com', '13800138001', 1, '热爱技术，分享经验', 1),
('lisi', '123456', '李四', 'https://cube.elemecdn.com/9/b5/469d81f72e82c6030j.png', 'lisi@guangzhi.com', '13800138002', 2, '产品经理', 1),
('wangwu', '123456', '王五', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', 'wangwu@guangzhi.com', '13800138003', 1, '全栈开发者', 1),
('zhaoliu', '123456', '赵六', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 'zhaoliu@guangzhi.com', '13800138004', 2, '开源爱好者', 1),
('sunqi', '123456', '孙七', 'https://cube.elemecdn.com/9/b5/469d81f72e82c6030j.png', 'sunqi@guangzhi.com', '13800138005', 1, '数据分析师', 1),
('zhouba', '123456', '周八', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', 'zhouba@guangzhi.com', '13800138006', 2, 'UI设计师', 1);

INSERT INTO `user_relation` (`user_id`, `target_user_id`, `relation_type`, `status`) VALUES
(1, 2, 1, 1),
(1, 3, 1, 1),
(1, 4, 1, 1),
(1, 5, 1, 1),
(2, 1, 1, 1),
(2, 3, 1, 1),
(3, 1, 1, 1),
(3, 2, 1, 1),
(4, 2, 1, 1),
(4, 5, 1, 1),
(5, 1, 1, 1),
(5, 2, 1, 1),
(6, 2, 1, 1),
(7, 3, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;

USE weibo_content;

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE content_tag;
TRUNCATE TABLE content;
TRUNCATE TABLE tag;

INSERT INTO `content` (`user_id`, `title`, `summary`, `content`, `content_type`, `status`, `view_count`, `like_count`, `comment_count`, `share_count`) VALUES
(1, 'SpringCloud微服务架构详解', '深入浅出讲解SpringCloud微服务架构的核心概念与实践', 'SpringCloud是一系列框架的有序集合，利用Spring Boot的开发便利性巧妙地简化了分布式系统基础设施的开发。\n\n通过Spring Cloud开发的应用程序很容易在分布式系统中进行构建，它们兼具简单、可维护和可测试性。\n\n微服务架构是一种架构风格，它将单个应用程序划分为一组小服务，服务之间互相协调、互相配合。每个服务运行在独立进程中，服务与服务之间用轻量级通信机制（HTTP）进行沟通。', 1, 1, 12580, 1024, 328, 156),
(2, 'Vue3组合式API实战', '从零开始学习Vue3的组合式API，提升代码组织能力', 'Vue3引入了组合式API，让我们可以更灵活地组织组件逻辑。通过setup函数，我们可以使用响应式api、计算属性、监听器等，让代码更加简洁和可复用。\n\n组合式API是Vue3最重要的特性之一，它解决了Vue2中逻辑复用困难的问题，让代码组织更加清晰。', 1, 1, 9876, 876, 245, 128),
(3, 'Redis缓存优化技巧', '分享Redis在实际项目中的性能优化经验', 'Redis作为高性能的键值数据库，在分布式系统中常用于缓存、会话存储、消息队列等场景。合理使用Redis可以大幅提升系统性能。\n\n本文分享一些在实际项目中的Redis优化技巧，包括缓存策略、内存优化、集群部署等方面。', 1, 1, 8654, 765, 189, 98),
(3, 'Kafka消息队列实战', '详解Kafka的安装配置与使用', 'Kafka是一种分布式流处理平台，可以实时处理大量数据消息。本文介绍Kafka的基本概念、安装配置和Java集成。\n\nKafka最初由LinkedIn开发，后成为Apache顶级项目。它具有高吞吐量、低延迟、可扩展等特点。', 1, 1, 7654, 654, 167, 87),
(2, 'Elasticsearch搜索引擎入门', 'ES从入门到精通完全指南', 'Elasticsearch是一个基于Lucene的搜索服务器，提供了分布式全文搜索功能。本文详细介绍ES的安装、配置和使用方法。\n\nElasticsearch常用于全文搜索、日志分析、业务搜索等场景，是现代分布式系统中不可或缺的组件。', 1, 1, 6543, 543, 145, 76),
(4, 'MySQL性能优化实战', '数据库性能优化必看', 'MySQL是最流行的关系型数据库之一，本文分享一些MySQL性能优化的实战经验，包括索引优化、查询优化、配置优化等方面。\n\n通过对SQL语句的执行计划分析，我们可以找到性能瓶颈并进行针对性优化。', 1, 1, 5432, 432, 123, 65),
(5, 'Docker容器化部署', 'Docker与Kubernetes实战', 'Docker是一个开源的容器化平台，可以将应用程序及其依赖打包成容器，实现一致的部署环境。\n\n本文介绍Docker的基本使用和Dockerfile的编写，以及Kubernetes入门。', 1, 1, 4321, 321, 98, 54),
(4, 'SpringBoot3新特性', 'SpringBoot3.0新功能介绍', 'Spring Boot 3.0带来了许多新特性，包括GraalVM原生支持、Observability改进等。\n\n本文详细介绍Spring Boot 3.0的新特性和升级注意事项。', 1, 1, 3210, 210, 76, 43);

INSERT INTO `tag` (`name`, `description`, `content_count`) VALUES
('SpringCloud', 'SpringCloud微服务框架', 1),
('Vue3', 'Vue3前端框架', 1),
('Redis', 'Redis缓存数据库', 1),
('微服务', '微服务架构', 3),
('性能优化', '系统性能优化', 2),
('Kafka', 'Kafka消息队列', 1),
('Elasticsearch', 'Elasticsearch搜索引擎', 1),
('MySQL', 'MySQL数据库', 1),
('Docker', 'Docker容器化', 1),
('SpringBoot', 'SpringBoot框架', 1),
('前端', '前端技术', 1),
('后端', '后端技术', 5);

INSERT INTO `content_tag` (`content_id`, `tag_id`) VALUES
(1, 1), (1, 4),
(2, 2), (2, 11),
(3, 3), (3, 5),
(4, 6),
(5, 7),
(6, 8), (6, 5),
(7, 9),
(8, 10), (8, 4);

SET FOREIGN_KEY_CHECKS = 1;

USE weibo_interaction;

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE notification;
TRUNCATE TABLE interaction;

INSERT INTO `interaction` (`user_id`, `content_id`, `type`, `content`) VALUES
(1, 1, 1, NULL),
(2, 1, 1, NULL),
(3, 1, 1, NULL),
(4, 1, 1, NULL),
(5, 1, 1, NULL),
(6, 1, 1, NULL),
(7, 1, 1, NULL),
(1, 2, 1, NULL),
(3, 2, 1, NULL),
(4, 2, 1, NULL),
(5, 2, 1, NULL),
(1, 3, 1, NULL),
(2, 3, 1, NULL),
(4, 3, 1, NULL),
(1, 4, 1, NULL),
(5, 4, 1, NULL),
(2, 5, 1, NULL),
(3, 5, 1, NULL),
(5, 5, 1, NULL),
(2, 6, 1, NULL),
(1, 7, 1, NULL),
(2, 8, 1, NULL),
(3, 8, 1, NULL),
(1, 2, 2, '非常实用的文章，学习了！'),
(3, 1, 2, '感谢分享，讲解得很详细'),
(4, 2, 2, '组合式API确实很好用，已收藏'),
(1, 3, 2, '支持一下！'),
(2, 4, 2, 'Kafka入门的好文章'),
(3, 5, 2, 'ES讲得很清晰'),
(5, 6, 2, 'mysql优化很有帮助'),
(6, 1, 2, '写的真好！'),
(7, 2, 2, 'mark一下'),
(1, 1, 3, NULL),
(2, 2, 3, NULL),
(3, 3, 3, NULL);

INSERT INTO `notification` (`user_id`, `from_user_id`, `content_id`, `type`, `content`, `status`) VALUES
(1, 2, 1, 1, '张三赞了你的内容', 0),
(1, 3, 1, 1, '李四赞了你的内容', 0),
(1, 3, 1, 2, '李四评论了你的内容', 0),
(1, 4, 1, 3, '王五关注了你', 0),
(1, 5, 1, 3, '赵六关注了你', 0),
(1, 6, 1, 3, '孙七关注了你', 0),
(1, 7, 1, 3, '周八关注了你', 0),
(2, 1, 2, 3, '系统管理员关注了你', 1),
(3, 1, 3, 3, '系统管理员关注了你', 1),
(4, 1, 4, 3, '系统管理员关注���你', 0),
(5, 1, 5, 3, '系统管理员关注了你', 0),
(2, 3, 1, 2, '李四评论了你的内容', 0),
(3, 2, 2, 1, '张三赞了你的内容', 0),
(4, 2, 2, 1, '张三赞了你的内容', 0),
(5, 3, 3, 1, '李四赞了你的内容', 0),
(1, 2, 1, 2, '张三 评论了你的内容：非常实用的文章', 0),
(1, 4, 2, 2, '王五 评论了你的内容：支持一下', 0);

SET FOREIGN_KEY_CHECKS = 1;

SELECT '数据初始化完成！' AS message;