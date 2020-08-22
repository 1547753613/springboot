package com.aaa.springboothomestay.controller;

import com.aaa.springboothomestay.entity.Admins;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@Controller
@RequestMapping("test")
public class TestController {

    @RequestMapping("/getAuth")
    @ResponseBody
    public Admins getUser4(Authentication authentication){
        Admins person= (Admins) authentication.getPrincipal();

        return person;
    }
}
