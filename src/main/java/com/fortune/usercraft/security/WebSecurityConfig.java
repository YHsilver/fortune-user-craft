package com.fortune.usercraft.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortune.usercraft.config.ErrorCode;
import com.fortune.usercraft.controller.response.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.lang.model.type.NullType;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    private final PasswordAuthenticationFilter passwordAuthenticationFilter;
    private final ObjectMapper objectMapper;

    public WebSecurityConfig(PasswordAuthenticationFilter passwordAuthenticationFilter,
                             ObjectMapper objectMapper) {
        this.passwordAuthenticationFilter = passwordAuthenticationFilter;
        this.objectMapper = objectMapper;
    }

    @Bean @Override
    public CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable useless filters
        http.csrf().disable().formLogin().disable().logout().disable().httpBasic().disable();

        http.cors();
        // TODO: configure here:
        http.authorizeRequests()
                .antMatchers("/api/user/login",
                        "/api/user/registerLeader",
                        "/api/user/registerStudent",
                        "/api/user/current"
                ).permitAll()
                .anyRequest().authenticated();

        // config authentication method
        http.addFilterBefore(passwordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    logger.info("Request to {} meet AuthenticationException: {}",
                            request.getRequestURI(), authException.getMessage());
                    AppResponse<NullType> res = AppResponse.failure(ErrorCode.UNAUTHENTICATED, "用户未认证");
                    String body = objectMapper.writeValueAsString(res);
                    response.setContentType("application/json;charset=utf-8");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().println(body);
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    logger.info("Request to {} meet AccessDeniedException: {}",
                            request.getRequestURI(), accessDeniedException.getMessage());

                    AppResponse<NullType> res = AppResponse.failure(ErrorCode.UNAUTHORIZED, "无权访问");
                    String body = objectMapper.writeValueAsString(res);
                    response.setContentType("application/json;charset=utf-8");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().println(body);
                });
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // TODO: config to ignore static resource
//        web.ignoring().antMatchers("/**"); // only static resource
    }

    @Bean
    public CorsFilter corsFilter() {
        // config cors
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
