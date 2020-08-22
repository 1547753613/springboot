package com.aaa.springboothomestay.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("test")
public class TestController {

    @RequestMapping("getUser")
    @ResponseBody
    public Object getUser4(HttpServletRequest request){
        return request.getUserPrincipal();
    }

    @RequestMapping("aaa")
    @ResponseBody
    public String aaa(){return "aaa";}

    // admin角色可以访问
    @Secured("ROLE_ADMIN")
    @RequestMapping("m1")
    @ResponseBody
    public String m1(){return "m1";}

    @Secured("ROLE_TEST")
    @RequestMapping("m2")
    @ResponseBody
    public String m2(){return "m2";}

    @Secured({"ROLE_ADMIN","ROLE_TEST"})
    @RequestMapping("m3")
    @ResponseBody
    public String m3(){return "m3";}

    @Secured({"ADMIN","ROLE_TEST"})
    @RequestMapping("m4")
    @ResponseBody
    public String m4(){return "m4";}

    // 拒绝所有角色
    @DenyAll
    @RequestMapping("m5")
    @ResponseBody
    public String m5(){return "m5";}

    // 允许所有角色
    @PermitAll
    @RequestMapping("m6")
    @ResponseBody
    public String m6(){return "m6";}

    @RolesAllowed({"ROLE_ADMIN"})
    @RequestMapping("m7")
    @ResponseBody
    public String m7(){return "m7";}

    // 可以省略ROLE_
    @RolesAllowed({"ROLE_ADMIN","TEST"})
    @RequestMapping("m8")
    @ResponseBody
    public String m8(){return "m8";}

    // 只识别ROLE，不识别权限
    @RolesAllowed({"select"})
    @RequestMapping("m9")
    @ResponseBody
    public String m9(){return "m9";}

    // 只识别ROLE，不识别权限
    @Secured("select")
    @RequestMapping("m10")
    @ResponseBody
    public String m10(){return "m10";}


    // SecuredEnabled>jsr250Enabled
    // 不生效
    @RolesAllowed("ROLE_TEST")
    // 生效
    @Secured("ROLE_ADMIN")
    @RequestMapping("m11")
    @ResponseBody
    public String m11(){return "m11";}

    // 在方法调用前进行授权
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("m12")
    @ResponseBody
    public String m12(){
        System.out.println("m12");
        return "m12";
    }

    // 权限select可访问
    @PreAuthorize("hasAuthority('select')")
    @RequestMapping("m13")
    @ResponseBody
    public String m13(){
        System.out.println("m13");
        return "m13";
    }

    // 角色是ROLE_ADMIN，权限select可访问
    @PreAuthorize("hasRole('ROLE_ADMIN') and hasAuthority('select')")
    @RequestMapping("m14")
    @ResponseBody
    public String m14(){
        System.out.println("m14");
        return "m14";
    }

    // 认证用户名必须是zs可访问
    @PreAuthorize("principal.username.equals('zs')")
    @RequestMapping("m15")
    @ResponseBody
    public String m15(){
        System.out.println("m15");
        return "m15";
    }

    // 前置过滤
    @PreAuthorize("#pageNum > 0")
    @RequestMapping("m16")
    @ResponseBody
    public String m16(Integer pageNum){
        System.out.println("m16");
        return "m16";
    }

    @PostAuthorize("hasAuthority('add')")
    @RequestMapping("m17")
    @ResponseBody
    public String m17(){
        System.out.println("m17");
        return "m17";
    }
}
