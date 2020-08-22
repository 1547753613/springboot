package com.aaa.springboothomestay.config;

import com.aaa.springboothomestay.entity.Admins;
import com.aaa.springboothomestay.impl.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AdminConfig implements UserDetailsService {

    @Autowired
    AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Admins admins = adminService.FindAdminName(s);
        if (null==admins){
            throw new UsernameNotFoundException("用户名不存在");

        }
        return admins;
    }
}
