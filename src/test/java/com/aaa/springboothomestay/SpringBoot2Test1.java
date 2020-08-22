package com.aaa.springboothomestay;


import com.aaa.springboothomestay.entity.Admins;
import com.aaa.springboothomestay.entity.Menu;
import com.aaa.springboothomestay.entity.MenuRole;
import com.aaa.springboothomestay.entity.Role;
import com.aaa.springboothomestay.impl.service.AdminService;
import com.aaa.springboothomestay.impl.service.MenuRoleService;
import com.aaa.springboothomestay.impl.service.MenuService;
import com.aaa.springboothomestay.impl.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringBoot2Test1 {

    @Autowired
    AdminService adminService;

    @Autowired
    RoleService roleService;

    @Autowired
    MenuRoleService menuRoleService;
    @Autowired
    MenuService menuService;


    @Test
    public void t4(){
        Admins admins = adminService.FindAdminName("Admin");
        Integer rid = admins.getRid();
        Set<MenuRole> menuRoles = menuRoleService.SelectMenuRid(rid);
        List<Integer> lists=menuRoles.stream().map(MenuRoles->MenuRoles.getMid()).collect(Collectors.toList());

        List<Menu> menus = menuService.SelectMenuId(lists);
        Role role = roleService.SelectRoleId(rid);
        System.out.println(role);
        System.out.println(menus);
        System.out.println(menuRoles);
    }

    @Test
    public void t1(){
        System.out.println(1);
        System.out.println(menuService.SelectMenuParentId(2));
    }

}
