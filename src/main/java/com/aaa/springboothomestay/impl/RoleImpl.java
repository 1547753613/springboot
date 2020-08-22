package com.aaa.springboothomestay.impl;

import com.aaa.springboothomestay.dao.RoleDao;
import com.aaa.springboothomestay.entity.Role;
import com.aaa.springboothomestay.impl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class RoleImpl implements RoleService {

    @Resource
    RoleDao roleDao;

    /**
     *
     * @param rid 角色id
     * @return
     */
    @Override
    public Role SelectRoleId(Integer rid) {


        return roleDao.selectByPrimaryKey(rid);
    }
}
