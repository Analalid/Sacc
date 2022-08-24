package com.example.sacc.Config;
import com.example.sacc.Interceptor.AreaInterceptor;
import com.example.sacc.Interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    private static final String ALL = "/**";
    private static final String NORMAL= "/normal";
    /**
     * 解决跨域问题
     * @return
     */
    @Bean
    public AreaInterceptor getAreaInterceptor() {
        return new AreaInterceptor();
    }
    /**
     * 返回单例对象
     * @return
     */
    @Bean
    public TokenInterceptor getTokenInterceptor() {
        return new TokenInterceptor();
    }
    /**
     * addInterceptor(拦截器)--> 将拦截器注册进来
     * addPathPatterns("url")--> 添加需要拦截的请求
     * excludePathPatterns("url")-->添加放行的请求，即不拦截
     * <p>
     * 注意：如果要拦截所有请求，则拦截"/**"
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(getAreaInterceptor()).addPathPatterns(ALL);
//        registry.addInterceptor(getTokenInterceptor()).addPathPatterns(ALL).excludePathPatterns("/login").excludePathPatterns("/register");
    }
}
