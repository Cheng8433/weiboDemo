package com.weibo.interaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weibo.interaction.entity.Interaction;
import com.weibo.interaction.mapper.InteractionMapper;
import com.weibo.interaction.service.InteractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 互动服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InteractionServiceImpl implements InteractionService {
    
    private final InteractionMapper interactionMapper;
    
    // 互动类型常量
    private static final int INTERACTION_TYPE_LIKE = 1;      // 点赞
    private static final int INTERACTION_TYPE_COMMENT = 2;   // 评论
    private static final int INTERACTION_TYPE_COLLECT = 3;   // 收藏
    
    // 目标类型常量
    private static final int TARGET_TYPE_CONTENT = 1;        // 内容
    private static final int TARGET_TYPE_USER = 2;           // 用户
    
    @Override
    public void like(Long userId, Long targetId, Integer targetType) {
        log.info("用户点赞：userId={}, targetId={}, targetType={}", userId, targetId, targetType);
        
        // 检查是否已点赞
        LambdaQueryWrapper<Interaction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Interaction::getUserId, userId)
                .eq(Interaction::getTargetId, targetId)
                .eq(Interaction::getTargetType, targetType)
                .eq(Interaction::getInteractionType, INTERACTION_TYPE_LIKE);
        
        if (interactionMapper.selectCount(queryWrapper) > 0) {
            throw new RuntimeException("已经点赞过了");
        }
        
        // 创建点赞记录
        Interaction interaction = new Interaction();
        interaction.setUserId(userId);
        interaction.setTargetId(targetId);
        interaction.setTargetType(targetType);
        interaction.setInteractionType(INTERACTION_TYPE_LIKE);
        interaction.setCreateTime(LocalDateTime.now());
        
        interactionMapper.insert(interaction);
    }
    
    @Override
    public void unlike(Long userId, Long targetId, Integer targetType) {
        log.info("用户取消点赞：userId={}, targetId={}, targetType={}", userId, targetId, targetType);
        
        LambdaQueryWrapper<Interaction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Interaction::getUserId, userId)
                .eq(Interaction::getTargetId, targetId)
                .eq(Interaction::getTargetType, targetType)
                .eq(Interaction::getInteractionType, INTERACTION_TYPE_LIKE);
        
        Interaction interaction = interactionMapper.selectOne(queryWrapper);
        if (interaction != null) {
            interactionMapper.deleteById(interaction.getId());
        }
    }
    
    @Override
    public void comment(Long userId, Long targetId, Integer targetType, String content) {
        log.info("用户评论：userId={}, targetId={}, targetType={}, content={}", userId, targetId, targetType, content);
        
        Interaction interaction = new Interaction();
        interaction.setUserId(userId);
        interaction.setTargetId(targetId);
        interaction.setTargetType(targetType);
        interaction.setInteractionType(INTERACTION_TYPE_COMMENT);
        interaction.setContent(content);
        interaction.setCreateTime(LocalDateTime.now());
        
        interactionMapper.insert(interaction);
    }
    
    @Override
    public void collect(Long userId, Long targetId, Integer targetType) {
        log.info("用户收藏：userId={}, targetId={}, targetType={}", userId, targetId, targetType);
        
        // 检查是否已收藏
        LambdaQueryWrapper<Interaction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Interaction::getUserId, userId)
                .eq(Interaction::getTargetId, targetId)
                .eq(Interaction::getTargetType, targetType)
                .eq(Interaction::getInteractionType, INTERACTION_TYPE_COLLECT);
        
        if (interactionMapper.selectCount(queryWrapper) > 0) {
            throw new RuntimeException("已经收藏过了");
        }
        
        // 创建收藏记录
        Interaction interaction = new Interaction();
        interaction.setUserId(userId);
        interaction.setTargetId(targetId);
        interaction.setTargetType(targetType);
        interaction.setInteractionType(INTERACTION_TYPE_COLLECT);
        interaction.setCreateTime(LocalDateTime.now());
        
        interactionMapper.insert(interaction);
    }
    
    @Override
    public Map<String, Integer> getStats(Long targetId, Integer targetType) {
        log.info("获取互动统计：targetId={}, targetType={}", targetId, targetType);
        
        Map<String, Integer> stats = new HashMap<>();
        
        // 点赞数
        LambdaQueryWrapper<Interaction> likeQuery = new LambdaQueryWrapper<>();
        likeQuery.eq(Interaction::getTargetId, targetId)
                .eq(Interaction::getTargetType, targetType)
                .eq(Interaction::getInteractionType, INTERACTION_TYPE_LIKE);
        stats.put("likeCount", Math.toIntExact(interactionMapper.selectCount(likeQuery)));
        
        // 评论数
        LambdaQueryWrapper<Interaction> commentQuery = new LambdaQueryWrapper<>();
        commentQuery.eq(Interaction::getTargetId, targetId)
                .eq(Interaction::getTargetType, targetType)
                .eq(Interaction::getInteractionType, INTERACTION_TYPE_COMMENT);
        stats.put("commentCount", Math.toIntExact(interactionMapper.selectCount(commentQuery)));
        
        // 收藏数
        LambdaQueryWrapper<Interaction> collectQuery = new LambdaQueryWrapper<>();
        collectQuery.eq(Interaction::getTargetId, targetId)
                .eq(Interaction::getTargetType, targetType)
                .eq(Interaction::getInteractionType, INTERACTION_TYPE_COLLECT);
        stats.put("collectCount", Math.toIntExact(interactionMapper.selectCount(collectQuery)));
        
        // 分享数（暂未实现）
        stats.put("shareCount", 0);
        
        return stats;
    }
    
    @Override
    public List<Interaction> getUserInteractions(Long userId, Integer interactionType, Integer page, Integer size) {
        LambdaQueryWrapper<Interaction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Interaction::getUserId, userId)
                .orderByDesc(Interaction::getCreateTime);
        
        if (interactionType != null) {
            queryWrapper.eq(Interaction::getInteractionType, interactionType);
        }
        
        // 分页查询
        int offset = (page - 1) * size;
        queryWrapper.last("LIMIT " + offset + ", " + size);
        
        return interactionMapper.selectList(queryWrapper);
    }
}