package com.weibo.content.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ContentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private String username;
    private String userAvatar;
    private String title;
    private String summary;
    private String content;
    private String coverImage;
    private List<String> images;
    private String videoUrl;
    private Integer contentType;
    private Integer status;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Integer shareCount;
    private Integer collectCount;
    private Boolean liked;
    private Boolean collected;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}