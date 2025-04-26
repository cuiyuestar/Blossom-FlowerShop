package com.blossom.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blossom.constant.MessageConstant;
import com.blossom.constant.StatusConstant;
import com.blossom.context.BaseContext;
import com.blossom.dto.UserInfoDTO;
import com.blossom.dto.UserLoginDTO;
import com.blossom.entity.Employee;
import com.blossom.entity.User;
import com.blossom.exception.*;
import com.blossom.mapper.UserMapper;
import com.blossom.mapper.UsersMapper;
import com.blossom.properties.WeChatProperties;
import com.blossom.result.Result;
import com.blossom.service.UserService;
import com.blossom.utils.HttpClientUtil;
import com.blossom.utils.UserHolder;
import com.blossom.vo.UserInfoVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.blossom.constant.RedisConstants.USER_SIGN_KEY;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UsersMapper, User> implements UserService {

    //微信登录Api
    private static final String WX_LOGIN="https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

//    public User login(UserLoginDTO userLoginDTO) {
//        //调用微信接口服务，获得当前微信用户的openId
//        Map<String,String> map=new HashMap<>();
//        map.put("appid",weChatProperties.getAppid()); //获取本地配置好的appid和secret，并存入map
//        map.put("secret",weChatProperties.getSecret());
//        map.put("js_code",userLoginDTO.getCode()); //将小程序传到后端的校验码放入map
//        map.put("grant_type","authorization_code");
//        //通过该方法访问api地址（map是访问时携带的参数），访问到一个json字符串
//        String json=HttpClientUtil.doGet(WX_LOGIN,map);
//        //将json字符串构造成json对象
//        JSONObject jsonObject= JSON.parseObject(json);
//        //将openid取出来
//        String openid=jsonObject.getString("openid");
//
//        //判断openid是否为空，如果为空，则抛出异常
//        if(openid==null){
//            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
//        }
//        //判断当前用户是否为新用户
//        User user=userMapper.getByOpenid(openid);
//
//        //如果是新用户，则自动完成注册（将封装新用户数据的对象user插入数据库）
//        if(user==null){
//            user=User.builder()
//                    .openid(openid)
//                    .createTime(LocalDateTime.now()) //注册时间
//                    .build();
//            userMapper.insert(user); //将新用户数据插入数据库，完成注册
//        }
//        return user;
//    }

    /**
     * 统计用户签到
     * @return
     */
    @Override
    public Result signCount() {
        //1.获取当前登录用户
        Long userId = UserHolder.getUser().getId();
        //2.获取日期
        LocalDateTime now = LocalDateTime.now();
        //3.拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key= USER_SIGN_KEY+userId+keySuffix;
        //4.获取今天是本月第几天
        int dayOfMonth = now.getDayOfMonth();
        //5.获取本月截止今天所有的签到记录 BITFIELD sign:1004:20241111 GET u11 0
        List<Long> result = stringRedisTemplate.opsForValue().bitField(
                key, BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0)
        );
        if (result==null||result.isEmpty()){
            //没有任何签到结果
            return Result.success(0);
        }
        //6.循环遍历
        Long num = result.get(0);
        if (num==null||num==0){
            return Result.success(0);
        }
        int count=0;
        while(true){
            //7.让这个数字与1做与运算，得到数字的最后一个bit位//判断这个bit位是否为0
            if((num&1)==0){
                //如果为0，说明未签到，结束
                break;
            }else {
                //如果不为0，说明已签到，计数器+1;
                count++;
            }
            //把数字右移，抛弃最后一个bit位，继续下一个bit位
            num>>>=1;
        }
        return Result.success(count);

    }

    /**
     * 用户签到
     * @return
     */
    @Override
    public Result sign() {
        //1.获取当前登录用户
        Long userId = UserHolder.getUser().getId();
        //2.获取日期
        LocalDateTime now = LocalDateTime.now();
        //3.拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key= USER_SIGN_KEY+userId+keySuffix;
        //4.获取今天是本月第几天
        int dayOfMonth = now.getDayOfMonth();
        //5.写入redis SETBIT key offset 1
        stringRedisTemplate.opsForValue().setBit(key,dayOfMonth-1,true);
        return Result.success();
    }

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    public User login(UserLoginDTO userLoginDTO) {
        String username=userLoginDTO.getUsername();
        String password=userLoginDTO.getPassword();
        //判断当前用户是否为新用户
        User user=userMapper.getByUsername(username);

        //处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        return user;
    }


    /**
     * 修改用户信息
     * @param userInfoDTO
     */
    public void modifyInfo(UserInfoDTO userInfoDTO) {
        Long userId= BaseContext.getCurrentId();
        log.info("调用缓存用户id：{}",userId);
        User user=userMapper.getById(userId);
        user.setName(userInfoDTO.getName());
        user.setPhone(userInfoDTO.getPhone());
        user.setSex(userInfoDTO.getSex());
        user.setIdNumber(userInfoDTO.getIdNumber());
        userMapper.update(user);
    }

    /**
     * 用户注册
     * @param userLoginDTO
     * @return
     */
    public User register(UserLoginDTO userLoginDTO) {
        String username=userLoginDTO.getUsername();
        User user=userMapper.getByUsername(username);
        if(user==null){
            user=User.builder()
                    .username(username)
                    .password(userLoginDTO.getPassword())
                    .build();
        }else{
            //用户已存在，注册失败
            throw new RegisterFailedException(MessageConstant.REGISTER_FAILED);
        }
        userMapper.insert(user);
        return user;
    }

    /**
     * 获取用户信息
     * @return
     */
    public UserInfoVO getUserInfo() {
        Long userId=BaseContext.getCurrentId();
        User user=userMapper.getById(userId);
        return UserInfoVO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .phone(user.getPhone())
                .sex(user.getSex())
                .idNumber(user.getIdNumber())
                .build();
    }


}
