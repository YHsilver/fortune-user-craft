package com.fortune.usercraft.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordAuthenticationFilter passwordAuthenticationFilter;

    public WebSecurityConfig(PasswordAuthenticationFilter passwordAuthenticationFilter) {
        this.passwordAuthenticationFilter = passwordAuthenticationFilter;
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
        http.cors();
        http.csrf().disable().formLogin().disable().logout().disable();

        http.authorizeRequests()
                .antMatchers("/api/user/login",
                        "/api/user/register",
                        "/api/user/current"
                ).permitAll()
                .anyRequest().authenticated();

        // config student authentication method
        http.addFilterBefore(passwordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // todo: add leader

        // todo: configure here
        http.exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.getWriter().println(authException.getMessage());
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.getWriter().println(accessDeniedException.getMessage());
                });
        // todo: 可以直接配置这里
//        http.formLogin().successHandler().failureHandler().failureForwardUrl()
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/h2-console/**");
//        web.ignoring().antMatchers("/**"); // only static resource
    }

    // Used by spring security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
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
