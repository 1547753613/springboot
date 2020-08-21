package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class Landlord {
    private int lid;//	int 	主键编号
    private String nickname;//	varchar	昵称
    private String realname;//	varchar	真实姓名
    private String idcard;//	varchar	身份证
    private String idcardimg;//	varchar	身份证照片
    private String head;//	varchar	头像
    private String phone;//	varchar	手机号
    private String pass;//	varchar	密码
    private String nativePlave;//	varchar	籍贯
    private String city;//	varchar	市区
    private String address;//	varchar	详情地址
    private String email;//	varchar	邮箱
    private String account;//varchar	账户
    private int state;//	int	状态
    private String greeting;//	varchar	房东欢迎语
}
