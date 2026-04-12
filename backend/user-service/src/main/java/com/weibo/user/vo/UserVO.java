package com.weibo.user.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String email;
    private String phone;
    private Integer gender;
    private String bio;
    private LocalDateTime birthday;
    private Integer status;
    private LocalDateTime createTime;
    private String token;
}