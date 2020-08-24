package com.aaa.springboothomestay.controller;

import com.aaa.springboothomestay.aliyun.ALiNote;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("denglu")
public class TextController {
    @RequestMapping("/yuan")
    public String yuan()
    {
        return "index";
    }
    //发送短信
    @PostMapping(value = "/sendCode")
    public Object sendCode(String phone , HttpServletRequest res){
        try{
            ALiNote.sendSms(phone,res);
        }catch (Exception e){
            res.setAttribute("msg","发送失败");
        }
        return "luo";
    }

}
