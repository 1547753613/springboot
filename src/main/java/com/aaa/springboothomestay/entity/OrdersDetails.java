package com.aaa.springboothomestay.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrdersDetails {
    private int id;//	int	主键
    private String oid;//	varchar	订单表外键
    private Date checkdate;//	date	入住时间
    private Date leavedate;//	date	离开时间
    private int day;//	int	天数
    private  int housecount;//	int	房屋套数
    private int checkcount;//	int	入住人数
    private String checkname;//	varchar	入住人
    private String phone;//	varchar	手机号
    private Double price;//	double	订单总价
    private Double amount;//	double	实付金额
    private Date paytime;//	date	支付时间
    private Date totime;//	date	实际入住时间
    private Date gotime;//	date	实际离开时间

}
