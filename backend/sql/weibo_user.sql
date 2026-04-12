-- 微博分布式内容发布与互动平台 - 用户数据库初始化脚本
-- Database: weibo_user

CREATE DATABASE IF NOT EXISTS weibo_user DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE weibo_user;

-- 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` BIGINT(20) NOT NULL auto_increment COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `gender` TINYINT(1) DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
    `bio` VARCHAR(500) DEFAULT NULL COMMENT '个人简介',
    `birthday` DATE DEFAULT NULL COMMENT '生日',
    `status` TINYINT(1) DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '是否删除：0-否，1-是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    KEY `idx_email` (`email`),
    KEY `idx_phone` (`phone`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 用户关系表（关注/粉丝）
DROP TABLE IF EXISTS `user_relation`;
CREATE TABLE `user_relation` (
    `id` BIGINT(20) NOT NULL auto_increment COMMENT '关系ID',
    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
    `target_user_id` BIGINT(20) NOT NULL COMMENT '目标用户ID',
    `relation_type` TINYINT(1) DEFAULT 1 COMMENT '关系类型：1-关注',
    `status` TINYINT(1) DEFAULT 1 COMMENT '状态：0-取消，1-有效',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_target` (`user_id`, `target_user_id`, `relation_type`),
    KEY `idx_target_user_id` (`target_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户关系表';

-- 插入测试数据
INSERT INTO `user` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`) VALUES
(1, 'admin', '123456', '系统管理员', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', 'admin@weibo.com', '13800138000', 1, '系统管理员', 1),
(2, 'zhangsan', '123456', '张三', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 'zhangsan@weibo.com', '13800138001', 1, '技术爱好者', 1),
(3, 'lisi', '123456', '李四', 'https://cube.elemecdn.com/9/b5/469d81f72e82c6030j.png', 'lisi@weibo.com', '13800138002', 2, '产品经理', 1);

-- 插入测试关系数据
INSERT INTO `user_relation` (`id`, `user_id`, `target_user_id`, `relation_type`, `status`) VALUES
(1, 1, 2, 1, 1),
(2, 1, 3, 1, 1),
(3, 2, 1, 1, 1),
(4, 3, 1, 1, 1);