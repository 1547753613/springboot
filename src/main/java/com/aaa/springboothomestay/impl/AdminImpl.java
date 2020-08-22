package com.aaa.springboothomestay.impl;

import com.aaa.springboothomestay.dao.AdminDao;
import com.aaa.springboothomestay.entity.Admins;
import com.aaa.springboothomestay.impl.service.AdminService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class AdminImpl implements AdminService {

    @Resource
    AdminDao adminDao;

    /**
     *
     * @param name 用户名
     * @param pwd   密码
     * @return
     */
    @Override
    public Admins Login(String name, String pwd) {
        return null;
    }

    /**
     *
     * @param name 根据name查询是否存在该员工
     * @return
     */
    @Override
    public Admins FindAdminName(String name) {
        Admins admins=new Admins();
        admins.setAname(name);
        List<Admins> list = adminDao.select(admins);
        if (list.size()==1){
            return list.get(0);
        }
        return null;
    }
}
