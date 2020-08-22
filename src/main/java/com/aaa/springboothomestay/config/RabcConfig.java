package com.aaa.springboothomestay.config;

import com.aaa.springboothomestay.entity.Admins;
import com.aaa.springboothomestay.entity.Menu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Component("rbacConfig")
public class RabcConfig {
    public boolean isForbidden(HttpServletRequest request, Authentication authentication) throws Exception {
        Object principal = authentication.getPrincipal();
        // 获取请求路径
        String uri = request.getRequestURI();
        // 从数据库中获取用户权限

        // 判断用户是否拥有该操作权限
       // System.out.println(uri);
        if(principal instanceof UserDetails){

            Admins admins= (Admins) principal;
            List<Menu> menus = admins.getMenus();
            List<String>urls=menus.stream().map(Menu->Menu.getUrl()).collect(Collectors.toList());

            if (urls.contains(uri)){
               // System.out.println("true");
                return true;
            }

            //判断是否是全部可用得
            for (String s : urls){
                    if (s.indexOf("/**")!=-1){

                        String substring = StringUtils.substring(uri, 0, StringUtils.lastIndexOf(uri, "/"));
                        if (s.contains(substring)){
                            return  true;
                        }
                    }
            }


        }
        return false;

    }

    public static void main(String[] args) {
        String url="/PermitAll/succe";
        System.out.println(StringUtils.substring(url,0,StringUtils.lastIndexOf(url,"/")));
    }
}
