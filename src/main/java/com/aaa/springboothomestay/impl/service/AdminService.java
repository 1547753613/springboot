package com.aaa.springboothomestay.impl.service;

import com.aaa.springboothomestay.entity.Admins;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    /**
     * 登录
     * @param name 用户名
     * @param pwd   密码
     * @return
     */
    public Admins Login(String name,String pwd);


    /**
     *
     * @param name 根据name查询是否存在该员工
     * @return
     */
    public Admins FindAdminName(String name);
}
