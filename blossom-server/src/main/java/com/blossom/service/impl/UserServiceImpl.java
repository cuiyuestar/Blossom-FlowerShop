package com.blossom.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blossom.constant.MessageConstant;
import com.blossom.dto.UserLoginDTO;
import com.blossom.entity.User;
import com.blossom.exception.LoginFailedException;
import com.blossom.mapper.UserMapper;
import com.blossom.properties.WeChatProperties;
import com.blossom.service.UserService;
import com.blossom.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    //微信登录Api
    private static final String WX_LOGIN="https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    public User login(UserLoginDTO userLoginDTO) {
        //调用微信接口服务，获得当前微信用户的openId
        Map<String,String> map=new HashMap<>();
        map.put("appid",weChatProperties.getAppid()); //获取本地配置好的appid和secret，并存入map
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",userLoginDTO.getCode()); //将小程序传到后端的校验码放入map
        map.put("grant_type","authorization_code");
        //通过该方法访问api地址（map是访问时携带的参数），访问到一个json字符串
        String json=HttpClientUtil.doGet(WX_LOGIN,map);
        //将json字符串构造成json对象
        JSONObject jsonObject= JSON.parseObject(json);
        //将openid取出来
        String openid=jsonObject.getString("openid");

        //判断openid是否为空，如果为空，则抛出异常
        if(openid==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //判断当前用户是否为新用户
        User user=userMapper.getByOpenid(openid);

        //如果是新用户，则自动完成注册（将封装新用户数据的对象user插入数据库）
        if(user==null){
            user=User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now()) //注册时间
                    .build();
            userMapper.insert(user); //将新用户数据插入数据库，完成注册
        }
        return user;
    }
}
