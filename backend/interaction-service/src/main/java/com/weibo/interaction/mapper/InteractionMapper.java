package com.weibo.interaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weibo.interaction.entity.Interaction;
import org.apache.ibatis.annotations.Mapper;

/**
 * 互动Mapper接口
 */
@Mapper
public interface InteractionMapper extends BaseMapper<Interaction> {
}