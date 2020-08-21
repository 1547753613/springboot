package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class Specifictype {
    private int sid;//	int 	主键id
    private String sname;//	varchar	房源具体类型
    private int id;//	int 	外键房源类型表

}
