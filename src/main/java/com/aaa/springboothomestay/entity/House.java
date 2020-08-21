package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class House {
    private int id;//	int 	主键id
    private String hname;//	varchar	房源名称
    private String simg;//	varchar	封面图
    private String himg;//	varchar	详情图
    private int lid;//	int 	外键房东表
    private int rid;//	int 	外键出租方式表
    private int sid;//	int 	房源具体类型表
    private String feature;//	varchar	房源特色
    private Double xcoord;//	double	地图x轴
    private Double ycoord;//	double	地图y轴
    private String traffic;//	varchar	交通位置
    private int state;//	int 	状态
    private String rim;//	varchar	周边介绍
}
