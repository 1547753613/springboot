package com.aaa.springboothomestay.impl;

import com.aaa.springboothomestay.dao.AdminDao;
import com.aaa.springboothomestay.entity.Admins;
import com.aaa.springboothomestay.entity.Menu;
import com.aaa.springboothomestay.entity.MenuRole;
import com.aaa.springboothomestay.impl.service.AdminService;
import com.aaa.springboothomestay.impl.service.MenuRoleService;
import com.aaa.springboothomestay.impl.service.MenuService;
import com.aaa.springboothomestay.impl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class AdminImpl implements AdminService {

    @Resource
    AdminDao adminDao;
    @Autowired
    MenuRoleService menuRoleService;

    @Autowired
    MenuService menuService;

    @Autowired
    RoleService roleService;

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
            Admins admin = list.get(0);
            Set<MenuRole> menuRoles = menuRoleService.SelectMenuRid(admin.getRid());
            List<Integer> lists=menuRoles.stream().map(MenuRoles->MenuRoles.getMid()).collect(Collectors.toList());
            List<Menu> menus = menuService.SelectMenuId(lists);
            admin.setRole(roleService.SelectRoleId(admin.getRid()));
            admin.setMenus(menus);

            return admin;
        }
        return null;
    }
}
