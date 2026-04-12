package com.weibo.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weibo.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}