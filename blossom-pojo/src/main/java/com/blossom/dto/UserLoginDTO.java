package com.blossom.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * C端用户登录
 */
@Data
public class UserLoginDTO implements Serializable {

    //private String code; //微信登录凭证code
    private String username;

    private String password;

}
