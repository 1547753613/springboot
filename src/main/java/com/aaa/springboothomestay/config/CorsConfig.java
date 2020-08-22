package com.aaa.springboothomestay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tk.mybatis.mapper.annotation.Order;

@Configuration
public class CorsConfig {

    @Bean
    @Order("0")
    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许携带验证信息
        corsConfiguration.setAllowCredentials(true);
        // 允许访问的域名
        // corsConfiguration.addAllowedOrigin("http://localhost:8081/");
        // corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:8081/","http://localhost:8082/"));
        corsConfiguration.addAllowedOrigin("*");
        // 允许的请求头信息
        corsConfiguration.addAllowedHeader("*");
        // 允许的请求方式
        corsConfiguration.addAllowedMethod("*");

        // 基于请求路径url跨域验证
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
