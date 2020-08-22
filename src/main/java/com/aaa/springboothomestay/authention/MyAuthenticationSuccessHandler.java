package com.aaa.springboothomestay.authention;


import com.aaa.springboothomestay.code.Result;
import com.aaa.springboothomestay.code.ResultCode;
import com.aaa.springboothomestay.code.ResultUtil;
import com.aaa.springboothomestay.entity.Admins;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        Admins admins= (Admins) authentication.getPrincipal();
        Map<String,Object>map=new HashMap<>();
        map.put("meg","登陆成功");
        map.put("admin",admins);
        PrintWriter out = httpServletResponse.getWriter();
        Result result = ResultUtil.success(ResultCode.SUCCESS, map);
        out.write(JSONObject.toJSONString(result));
        out.flush();
        out.close();
    }
}
