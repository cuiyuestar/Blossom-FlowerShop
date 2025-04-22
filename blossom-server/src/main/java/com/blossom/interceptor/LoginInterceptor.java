package com.blossom.interceptor;


import com.blossom.utils.UserHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Vast
 * @version 1.0
 * @function:使用ThreadLocal机制实现登录拦截策略
 */
@SuppressWarnings({"all"})
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.判断是否需要拦截（ThreadLocal中是否有用户）
        if(UserHolder.getUser()==null){
            //没有，需要拦截，设置状态码
            response.setStatus(401);
            //拦截
            return false;
        }
        //由用户放行
        return true;
    }
}
