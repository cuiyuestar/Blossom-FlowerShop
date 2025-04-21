package com.blossom.controller.user;


import com.blossom.constant.JwtClaimsConstant;
import com.blossom.context.BaseContext;
import com.blossom.dto.EmployeeLoginDTO;
import com.blossom.dto.UserInfoDTO;
import com.blossom.dto.UserLoginDTO;
import com.blossom.entity.Comment;
import com.blossom.entity.Employee;
import com.blossom.entity.User;
import com.blossom.properties.JwtProperties;
import com.blossom.result.Result;
import com.blossom.service.CommentService;
import com.blossom.service.UserService;
import com.blossom.utils.JwtUtil;
import com.blossom.vo.EmployeeLoginVO;
import com.blossom.vo.UserInfoVO;
import com.blossom.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Api(tags="C端相关接口")
@Slf4j

public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private CommentService commentService;


//    /**
//     * 微信登录
//     * @param userLoginDTO
//     * @return
//     */
//    @PostMapping("/login")
//    @ApiOperation("微信登录")
//    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
//        log.info("微信用户登录：{}",userLoginDTO.getCode());
//        //微信登录
//        User user=userService.login(userLoginDTO);
//
//        //生成Jwt令牌
//        Map<String,Object> claims=new HashMap<>();
//        claims.put(JwtClaimsConstant.USER_ID,user.getId()); //将从user对象中获取userId作为值，而“userId”作为键
//        String token=JwtUtil.createJWT(jwtProperties.getUserSecretKey(),jwtProperties.getUserTtl(),claims);
//
//        //封装数据
//        UserLoginVO userLoginVO=UserLoginVO.builder()
//                .id(user.getId())
//                .openid(user.getOpenid())
//                .token(token)
//                .build();
//
//        return Result.success(userLoginVO);
//    }

    /**
     * 用户注册
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result register(@RequestBody UserLoginDTO userLoginDTO){
        log.info("用户注册：{}",userLoginDTO.getUsername());
        User user=userService.register(userLoginDTO);
        return Result.success();
    }



    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("用户登录：{}",userLoginDTO.getUsername());

        User user=userService.login(userLoginDTO);

        //生成Jwt令牌
        Map<String,Object> claims=new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId()); //将从user对象中获取userId作为值，而“userId”作为键
        String token=JwtUtil.createJWT(jwtProperties.getUserSecretKey(),jwtProperties.getUserTtl(),claims);
        log.info("用户登录成功，生成Jwt令牌：{}",token);

        //封装数据
        UserLoginVO userLoginVO=UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }

    /**
     * 用户登出
     * @param session
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("用户退出登录")
    public Result logout(HttpSession session){
        log.info("用户退出登录");
        session.invalidate();  //销毁会话
        return Result.success("登出成功");
    }

    /**
     * 修改用户信息
     * @param userInfoDTO
     * @return
     */
    @PostMapping("/modifyInfo")
    @ApiOperation("修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "token",
                    value = "Bearer Token",
                    required = true,
                    paramType = "header"
            )
    })
    public Result modifyInfo( UserInfoDTO userInfoDTO){
        userService.modifyInfo(userInfoDTO);
        return Result.success("修改成功");
    }


    /**
     *查看个人信息
     * @return
     */
    @GetMapping("/getUserInfo")
    @ApiOperation("查看个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "token",
                    value = "Bearer Token",
                    required = true,
                    paramType = "header"
            )
    })
    public Result<UserInfoVO> getUserInfo(@RequestHeader("token") String token){
        return Result.success(userService.getUserInfo());
    }


    /**
     * 查询用户自己的评论 （用户自身评论数较少，可直接返回评论集合list）
     * @return
     */
    @GetMapping("/list-own-comment")
    @ApiOperation("查询用户自己的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "token",
                    value = "Bearer Token",
                    required = true,
                    paramType = "header"
            )
    })
    public Result<List<Comment>> listByUserId() {
        log.info("查询用户评论");
        Long userId= BaseContext.getCurrentId();
        List<Comment> commentList= commentService.listByUserId(userId);
        return Result.success(commentList);
    }


    /**
     * 点赞评论/取消点赞
     * @param
     * @return
     */
    @PostMapping("/{commentId}")
    @ApiOperation("点赞评论/取消点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "token",
                    value = "Bearer Token",
                    required = true,
                    paramType = "header"
            )
    })
    public Result likeComment(@PathVariable  Long commentId){
        Long userId=BaseContext.getCurrentId();
        log.info("点赞评论commentId：{}",commentId);
        log.info("点赞评论userId：{}",userId);
        commentService.like(commentId,userId);
        return Result.success();
    }


}
