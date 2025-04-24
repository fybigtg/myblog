package com.rethx.managesys.utils;



import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TokenUtils {

    //生成Token
    public static String genToken(String userId, String sign) {
        return JWT.create().withAudience(userId)    //将userId作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))   //2小时后Token过期
                .sign(Algorithm.HMAC256(sign));     //将password作为密钥
    }

}
