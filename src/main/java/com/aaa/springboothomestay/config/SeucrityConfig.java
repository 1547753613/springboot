package com.aaa.springboothomestay.config;


import com.aaa.springboothomestay.authention.MyAuthenticationFailureHandler;
import com.aaa.springboothomestay.authention.MyAuthenticationSuccessHandler;
import com.aaa.springboothomestay.filter.LoginFilter;
import com.aaa.springboothomestay.handler.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;


@Configuration
@EnableWebSecurity
public class SeucrityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SessionRegistry sessionRegistry;
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //登录失败
    @Autowired
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;
    //登录成功
    @Autowired
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    AdminConfig adminConfig;

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
      return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter=new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        loginFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);


        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/my");
        ConcurrentSessionControlAuthenticationStrategy sessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        sessionStrategy.setMaximumSessions(1);
        loginFilter.setSessionAuthenticationStrategy(sessionStrategy);

        return loginFilter;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder() );
        authenticationProvider.setUserDetailsService(adminConfig);

        auth.authenticationProvider(authenticationProvider);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(loginFilter(),UsernamePasswordAuthenticationFilter.class);

        http  .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
               .and()
    //设置表单方式提交
               .formLogin()
               // 登录页面
               //.loginPage("/index.html")
               // form表单提交的路径
              /* .loginProcessingUrl("/my")
               //默认为username,可替换为那么
               .usernameParameter("username")
               .passwordParameter("password")
               //登录成功后的跳转路径
               //.defaultSuccessUrl("/PermitAll/succe")
               *//*.successHandler(myAuthenticationSuccessHandler)
               .failureHandler(myAuthenticationFailureHandler)*//*
               //表示以上不进行认证其他统统认证
               .permitAll()*/
               //拼接条件
               .and()
               //设置请求路径
               .authorizeRequests()
               //表示该请求任何人都可以访问
               .antMatchers("/","/index.html","/static/**","/**.ico","/**.js","/**.svg","/createImageCode")
               .permitAll()

               //表示其他任意请求都要验证
               .anyRequest()
                .access("@rbacConfig.isForbidden(request,authentication)")
               .and()
               .cors()
               .and()
               //跨域请求
               .csrf()
               //禁用
               .disable()
               .authorizeRequests()
               // 验证跨域请求

               .and()
               .sessionManagement()
               .maximumSessions(1)
               .sessionRegistry(sessionRegistry())
                ;

       http.logout()
             .logoutUrl("/mylogout");//自定义退出url

     http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler); // 无权访问


    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/denglu/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico", "/verifyCode");
    }
}
