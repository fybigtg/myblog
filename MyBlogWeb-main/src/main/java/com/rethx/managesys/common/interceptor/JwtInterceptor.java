package com.rethx.managesys.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rethx.managesys.common.Constants;
import com.rethx.managesys.entity.User;
import com.rethx.managesys.exception.ServiceException;
import com.rethx.managesys.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor, Constants {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //如果不是映射到方法就直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //token为空
        if(StrUtil.isBlank(token)){
            throw new ServiceException(CODE_700,"访问异常");
        }
        //获取Token中的userId值
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            throw new ServiceException(CODE_700,"无效Token");
        }
        User user = userService.getUserById(Integer.parseInt(userId));
        if(user == null){
            throw new ServiceException(CODE_700,"用户不存在");
        }
        //用户密码验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPasswd())).build();
        try {
            jwtVerifier.verify(token);      //验证token
        } catch (JWTVerificationException e) {
            throw new ServiceException(CODE_700,"无效Token");
        }
        return true;
    }
}
