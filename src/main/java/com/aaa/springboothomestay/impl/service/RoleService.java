package com.aaa.springboothomestay.impl.service;

import com.aaa.springboothomestay.entity.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {

    /**
     *
     * @param rid 角色id
     * @return  根据id查角色
     */
    public Role SelectRoleId(Integer rid);
}
