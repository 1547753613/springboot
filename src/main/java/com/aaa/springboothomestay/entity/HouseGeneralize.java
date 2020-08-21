package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class HouseGeneralize {
    private int id;//	int 	主键id
    private int hid;//	int 	房屋外键id
    private Double area;//	double	整套面积
    private Double rarea;//	double	单间面积(默认为0，如果出租方式是整套则不显示该字段)
    private int cohabit;//	int 	房东是否与租客同住默认为不同住，如果出租类型是整套则不显示该字段
    private int bedroom;//	int 	卧室数量
    private int wc;//	int 	卫生间数量
    private int drawing;//	int 	客厅数量
    private int kitchen;//	int 	厨房数量
    private int balcony;//	int 	阳台数量
    private int wctype;//	int 	卫生间类型(0为公共卫生间,1为独立卫生间)
    private int count;//	int 	房源可预定套数

}
