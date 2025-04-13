package com.blossom.service;

import com.blossom.dto.UserInfoDTO;
import com.blossom.dto.UserLoginDTO;
import com.blossom.entity.User;
import com.blossom.vo.UserInfoVO;


public interface UserService {
    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    public User login(UserLoginDTO userLoginDTO);

    /**
     * 修改用户信息
     * @param userInfoDTO
     */
    void modifyInfo(UserInfoDTO userInfoDTO);

    /**
     * 注册新用户
     * @param userLoginDTO
     * @return
     */
    User register(UserLoginDTO userLoginDTO);

    /**
     * 获取用户信息
     * @return
     */
    UserInfoVO getUserInfo();
}
