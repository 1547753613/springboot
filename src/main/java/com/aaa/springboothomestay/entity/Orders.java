package com.aaa.springboothomestay.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Orders {
    private String id;//	varchar	主键
    private int hid;//	int	房屋表外键
    private int uid;//	int	用户表外键
    private int status;//	int	1待支付，2待入住，3待退房，4已完成，5已关闭，6待处理，7已处理,8维权订单'
    private String cdemo;//	varchar	取消原因说明
    private String protect;//	varchar	维权说明
    private String demo;//	varchar	订单处理结果
    private int aid;//	int	管理员id
    private Date createtime;//	date	订单创建时间

}
