package com.weibo.interaction.controller;

import com.weibo.interaction.service.InteractionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "互动管理", description = "互动相关接口")
@RestController
@RequestMapping("/interaction")
@RequiredArgsConstructor
public class InteractionController {
    
    private final InteractionService interactionService;
    
    @Operation(summary = "点赞")
    @PostMapping("/like")
    public Map<String, Object> like(
            @RequestParam Long userId,
            @RequestParam Long targetId,
            @RequestParam Integer targetType) {
        Map<String, Object> result = new HashMap<>();
        try {
            interactionService.like(userId, targetId, targetType);
            result.put("success", true);
            result.put("message", "点赞成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @Operation(summary = "取消点赞")
    @PostMapping("/unlike")
    public Map<String, Object> unlike(
            @RequestParam Long userId,
            @RequestParam Long targetId,
            @RequestParam Integer targetType) {
        Map<String, Object> result = new HashMap<>();
        try {
            interactionService.unlike(userId, targetId, targetType);
            result.put("success", true);
            result.put("message", "取消点赞成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @Operation(summary = "评论")
    @PostMapping("/comment")
    public Map<String, Object> comment(
            @RequestParam Long userId,
            @RequestParam Long targetId,
            @RequestParam Integer targetType,
            @RequestParam String content) {
        Map<String, Object> result = new HashMap<>();
        try {
            interactionService.comment(userId, targetId, targetType, content);
            result.put("success", true);
            result.put("message", "评论成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @Operation(summary = "收藏")
    @PostMapping("/collect")
    public Map<String, Object> collect(
            @RequestParam Long userId,
            @RequestParam Long targetId,
            @RequestParam Integer targetType) {
        Map<String, Object> result = new HashMap<>();
        try {
            interactionService.collect(userId, targetId, targetType);
            result.put("success", true);
            result.put("message", "收藏成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @Operation(summary = "获取互动统计")
    @GetMapping("/stats")
    public Map<String, Object> getStats(
            @RequestParam Long targetId,
            @RequestParam Integer targetType) {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Integer> stats = interactionService.getStats(targetId, targetType);
            result.put("success", true);
            result.put("data", stats);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}