package com.aaa.springboothomestay;


import com.aaa.springboothomestay.entity.Admins;
import com.aaa.springboothomestay.impl.service.AdminService;
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


    @Test
    public void t4(){
        Admins admins = adminService.FindAdminName("Admin");
        System.out.println(admins);
    }

}
