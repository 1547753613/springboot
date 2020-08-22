package com.aaa.springboothomestay.impl.service;

import com.aaa.springboothomestay.entity.MenuRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MenuRoleService {

    /**
     *
     * @param rid 角色id
     * @return 根据角色id查询权限
     */
    public Set<MenuRole> SelectMenuRid(int rid);
}
