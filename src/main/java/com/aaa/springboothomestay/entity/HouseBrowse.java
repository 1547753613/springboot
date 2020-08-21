package com.aaa.springboothomestay.entity;

import lombok.Data;

import java.util.Date;

@Data
public class HouseBrowse {
    private int id;//	int	主键
    private int hid;//	int	房屋表外键
    private int count;//	int	浏览次数
    private Date date;//	date	浏览日期

}
