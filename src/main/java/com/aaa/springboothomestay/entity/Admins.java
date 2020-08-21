package com.aaa.springboothomestay.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Admins {
    private int id;//主键编号
    private String aname;//账号
    private String apass;//密码
    private String head;//	头像
    private String name;///	varchar	姓名
    private String idcard;//	varchar	身份证
    private int gender;//	int 	性别
    private String nativePlave;//	varchar	籍贯
    private String address;//	varchar	地址
    private String phone;//	varchar	手机号
    private String email;//	varchar	邮箱
    private Date beginDate;//	date	入职日期
    private Date endDate;//	date	离职日期
    private int workState;//	int	状态
    private int rid;//	int	外键角色表
    private int isexpired;//	int	是否过期
    private int islocked;//	int	是否上锁
    private int isCreExpirEd;//	int	是否认证过期
    private int isenble;//	int 	是否禁用

}
