package com.weibo.content.controller;

import com.weibo.common.result.Result;
import com.weibo.content.dto.ContentDTO;
import com.weibo.content.service.ContentService;
import com.weibo.content.vo.ContentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "内容管理", description = "内容相关接口")
@RestController
@RequestMapping("/content")
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }
    
    @Operation(summary = "发布内容")
    @PostMapping
    public Result<ContentVO> publishContent(@Valid @RequestBody ContentDTO contentDTO) {
        return Result.success(contentService.publishContent(contentDTO));
    }
    
    @Operation(summary = "获取内容详情")
    @GetMapping("/{id}")
    public Result<ContentVO> getContentById(@PathVariable Long id) {
        return Result.success(contentService.getContentById(id));
    }
    
    @Operation(summary = "更新内容")
    @PutMapping("/{id}")
    public Result<ContentVO> updateContent(@PathVariable Long id, @Valid @RequestBody ContentDTO contentDTO) {
        return Result.success(contentService.updateContent(id, contentDTO));
    }
    
    @Operation(summary = "删除内容")
    @DeleteMapping("/{id}")
    public Result<Void> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return Result.success();
    }
    
    @Operation(summary = "获取内容列表")
    @GetMapping("/list")
    public Result<List<ContentVO>> getContentList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer contentType) {
        return Result.success(contentService.getContentList(page, size, contentType));
    }
    
    @Operation(summary = "获取用户内容列表")
    @GetMapping("/user/{userId}")
    public Result<List<ContentVO>> getUserContentList(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(contentService.getUserContentList(userId, page, size));
    }
    
    @Operation(summary = "增加浏览量")
    @PostMapping("/{id}/view")
    public Result<Void> incrementViewCount(@PathVariable Long id) {
        contentService.incrementViewCount(id);
        return Result.success();
    }
}