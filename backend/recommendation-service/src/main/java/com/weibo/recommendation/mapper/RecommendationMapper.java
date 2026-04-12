package com.weibo.recommendation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weibo.recommendation.entity.Recommendation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 推荐Mapper接口
 */
@Mapper
public interface RecommendationMapper extends BaseMapper<Recommendation> {
}