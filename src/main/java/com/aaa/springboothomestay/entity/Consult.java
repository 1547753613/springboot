package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class Consult {
    private int id;//	int 	主键id
    private String cname;// 	varchar  	问题描述
    private int parentid;//	int 	父id
}
