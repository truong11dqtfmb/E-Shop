package com.dqt.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class UserSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/shop/register","/shop/forgotPassword","/shop/processingRegistration",
                        "/shop/","/shop/category/**","/shop/product/**","/shop","/shop/home",
                        "/shop/register","/shop/processingRegistration",
                        "/shop/forgotPassword","/shop/processingForgotPassword",
                        "/shop/reset_password",
                        "/shop/verify").permitAll()
                .antMatchers("/shop/cart/**","/shop/checkout","/shop/processingCheckOut").hasAuthority("USER")
                .and()
                .formLogin()
                .loginPage("/shop/login")
                .usernameParameter("email")
                .loginProcessingUrl("/shop/processingLogin")
                .defaultSuccessUrl("/shop")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/shop/logout")
                .logoutSuccessUrl("/shop")
                .permitAll();

        return http.build();
    }
}
