package com.weibo.user.service;

import com.weibo.user.dto.UserDTO;
import com.weibo.user.vo.UserVO;

import java.util.List;

public interface UserService {
    UserVO register(UserDTO userDTO);
    UserVO login(UserDTO userDTO);
    UserVO getUserById(Long id);
    UserVO updateUser(UserDTO userDTO);
    void deleteUser(Long id);
    List<UserVO> getUserList();
}