package com.voter.config;

import com.voter.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns(
                        "/voter/regist",    // 注册接口不需要认证
                        "/voter/login",     // 登录接口不需要认证
                        "/error",           // 错误页面
                        "/swagger-ui/**",   // Swagger文档
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/webjars/**"
                );
        // 注意：/voter/logout 需要认证，所以不在排除列表中
    }
}
