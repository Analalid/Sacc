package com.example.sacc.Interceptor;
import com.example.sacc.Exception.LocalRuntimeException;
import com.example.sacc.Service.RedisService;
import com.example.sacc.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    RedisService redisService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        Object o = redisService.get(token);
        log.info("token:"+token);
        //123是万能token
        if (o==null){
            log.info("redis中没有token对应的角色");
            throw new LocalRuntimeException(ErrorEnum.Redis_NoAccount);
        }
        return true;
    }
}
