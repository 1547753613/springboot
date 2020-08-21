package com.aaa.springboothomestay.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserBrowse {
    private int id;//	int	主键
    private int uid;//	int	用户外键
    private int hid;//	int	房屋表外键
    private Date date;//	date	浏览日期
}
