package com.voter.interceptor;

import com.alibaba.fastjson.JSON;
import com.voter.auth.AuthUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证拦截器
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是OPTIONS请求，直接放行
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        String token = AuthUtil.getTokenFromRequest();

        if (!StringUtils.hasText(token)) {
            sendUnauthorizedResponse(response, "未提供认证信息");
            return false;
        }

        if (!AuthUtil.validateToken(token)) {
            sendUnauthorizedResponse(response, "无效的认证信息或认证信息已过期");
            return false;
        }

        return true;
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> result = new HashMap<>();
        result.put("code", 401);
        result.put("message", message);
        result.put("success", false);

        response.getWriter().write(JSON.toJSONString(result));
    }
}
