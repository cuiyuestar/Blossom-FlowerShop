package com.blossom.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blossom.dto.UserInfoDTO;
import com.blossom.dto.UserLoginDTO;
import com.blossom.entity.User;
import com.blossom.result.Result;
import com.blossom.vo.UserInfoVO;


public interface UserService extends IService<User> {
    /**
     * 统计用户签到
     * @return
     */
    Result signCount();

    /**
     * 用户签到
     * @return
     */
    Result sign();
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
