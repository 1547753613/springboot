package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class Housetype {
    private int id;//	int 	主键id
    private String hname;//	varchar	房源类型(别墅,四合院)
    private Double commission;//	double	抽成
    private String icon;//	varchar	图标
}
