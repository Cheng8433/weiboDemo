package com.weibo.user.controller;

import com.weibo.user.dto.UserDTO;
import com.weibo.user.entity.User;
import com.weibo.user.service.UserService;
import com.weibo.user.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "用户管理", description = "用户相关接口")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    
    private final UserService userService;
    
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public UserVO register(@Valid @RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }
    
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public UserVO login(@Valid @RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }
    
    @Operation(summary = "获取用户信息")
    @GetMapping("/{id}")
    public UserVO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    @Operation(summary = "更新用户信息")
    @PutMapping("/{id}")
    public UserVO updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        return userService.updateUser(userDTO);
    }
    
    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    
    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public List<UserVO> getUserList() {
        return userService.getUserList();
    }
}