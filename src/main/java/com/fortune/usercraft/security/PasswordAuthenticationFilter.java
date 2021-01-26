package com.fortune.usercraft.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortune.usercraft.config.ErrorCode;
import com.fortune.usercraft.controller.response.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.lang.model.type.NullType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Authenticate users by POST /api/{any}/login
 * Imitate {@code org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}
 */
@Component
public class PasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String[] PRINCIPLE_KEYS = {"phone", "username"};
    public static final String PASSWORD_KEY = "password";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/*/login",
            "POST");

    private final ObjectMapper objectMapper;

    public PasswordAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.setAuthenticationSuccessHandler((request, response, authentication) -> {
            AppResponse<NullType> res = AppResponse.success("登录成功");
            String body = objectMapper.writeValueAsString(res);
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(body);
        });
        this.setAuthenticationFailureHandler((request, response, exception) -> {
            AppResponse<NullType> res = AppResponse.failure(ErrorCode.UNKNOWN, "用户名密码不匹配");
            String body = objectMapper.writeValueAsString(res);
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(body);
        });
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        Map<String, Object> requestBody = objectMapper.readValue(
                request.getInputStream(),
                new TypeReference<Map<String, Object>>() {});
        String principle = obtainPrinciple(requestBody);
        principle = (principle != null) ? principle : "";
        principle = principle.trim();
        String password = obtainPassword(requestBody);
        password = (password != null) ? password : "";
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(principle, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Nullable
    protected String obtainPassword(Map<String, Object> request) {
        Object password = request.get(PASSWORD_KEY);
        if (password instanceof String) return (String) password;
        else return null;
    }

    @Nullable
    protected String obtainPrinciple(Map<String, Object> request) {
        for (String key : PRINCIPLE_KEYS) {
            Object principle = request.get(key);
            if (principle instanceof String) return (String) principle;
        }
        return null;
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    @Autowired @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
