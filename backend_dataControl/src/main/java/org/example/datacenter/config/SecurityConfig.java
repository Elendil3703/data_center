package org.example.datacenter.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // 禁用 CSRF 保护
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()  // 允许静态资源无条件访问
                        .anyRequest().authenticated())  // 其他所有请求都需要认证
                .formLogin(form -> form
                        .defaultSuccessUrl("/home", true)  // 登录成功后跳转的路径
                        .permitAll())  // 允许所有用户访问登录页面
                .logout(logout -> logout
                        .logoutUrl("/logout")  // 定义注销请求的URL
                        .logoutSuccessUrl("/login?logout"));  // 注销成功后跳转到登录页面，带参数logout
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}