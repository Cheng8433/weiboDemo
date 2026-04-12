package com.weibo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.weibo.common.util.JwtUtil;
import com.weibo.user.dto.UserDTO;
import com.weibo.user.entity.User;
import com.weibo.user.mapper.UserMapper;
import com.weibo.user.service.UserService;
import com.weibo.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    
    @Override
    public UserVO register(UserDTO userDTO) {
        log.info("用户注册：{}", userDTO.getUsername());
        
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userDTO.getUsername());
        if (userMapper.selectCount(queryWrapper) > 0) {
            throw new RuntimeException("用户名已存在");
        }
        
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setStatus(1);
        user.setDeleted(0);
        
        userMapper.insert(user);
        
        return convertToVO(user);
    }
    
    @Override
    public UserVO login(UserDTO userDTO) {
        log.info("用户登录：{}", userDTO.getUsername());
        
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userDTO.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        // 简单密码校验（实际项目中应该使用加密密码）
        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        UserVO userVO = convertToVO(user);
        // 生成JWT token
        String token = jwtUtil.generateToken(user.getId().toString(), user.getUsername());
        userVO.setToken(token);
        
        return userVO;
    }
    
    @Override
    public UserVO getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return convertToVO(user);
    }
    
    @Override
    public UserVO updateUser(UserDTO userDTO) {
        User user = userMapper.selectById(userDTO.getId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        BeanUtils.copyProperties(userDTO, user, "password");
        user.setUpdateTime(LocalDateTime.now());
        
        userMapper.updateById(user);
        
        return convertToVO(user);
    }
    
    @Override
    public void deleteUser(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 逻辑删除
        user.setDeleted(1);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }
    
    @Override
    public List<UserVO> getUserList() {
        List<User> users = userMapper.selectList(null);
        List<UserVO> list = new ArrayList<>();
        users.stream()
                .filter(user -> user.getDeleted() == 0)
                .forEach(user -> list.add(convertToVO(user)));
        return list;
    }
    
    private UserVO convertToVO(User user) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }
}