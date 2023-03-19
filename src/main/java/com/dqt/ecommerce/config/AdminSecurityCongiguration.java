package com.dqt.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(1)
public class AdminSecurityCongiguration {
    @Bean
    public UserDetailsService userDetailsService()
    {
        return new CustomUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/register","/admin/processingRegistration",
                        "/admin/forgotPassword","/admin/processingForgotPassword",
                        "/admin/reset_password",
                        "/admin/verify","/admin/css","/admin/js").permitAll();

        http.antMatcher("/admin/**").
                authorizeRequests().anyRequest().hasAuthority("ADMIN")
//                .antMatchers("/admin/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/admin/processingLogin")
                .defaultSuccessUrl("/admin")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login?logout")
                .permitAll();
        return http.build();
    }
}
