package com.fortune.usercraft.security;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Authenticate users by POST /api/{any}/login
 * Imitate {@code org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}
 */
@Component
public class PasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String[] FORM_ID_KEYS = {"phone", "username"};
    public static final String FORM_PASSWORD_KEY = "password";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/*/login",
            "POST");

    public PasswordAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println("{\"ecode\": \"0\", \"msg\": \"登录成功\"}");
        });
        this.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            // TODO: log
            response.getWriter().println("{\"ecode\": \"0\", \"msg\": \"用户名密码不匹配\"}");
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String id = obtainId(request);
        id = (id != null) ? id : "";
        id = id.trim();
        String password = obtainPassword(request);
        password = (password != null) ? password : "";
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(id, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(FORM_PASSWORD_KEY);
    }

    @Nullable
    protected String obtainId(HttpServletRequest request) {
        for (String key : FORM_ID_KEYS) {
            String value = request.getParameter(key);
            if (value != null) return value;
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
