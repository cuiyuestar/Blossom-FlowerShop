package com.blossom.service;

import com.blossom.dto.UserLoginDTO;
import com.blossom.entity.User;


public interface UserService {
    public User login(UserLoginDTO userLoginDTO);
}
