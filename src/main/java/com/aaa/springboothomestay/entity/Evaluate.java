package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class Evaluate {
    private int id;//	int 	主键id
    private int oid;//int	订单表外键
    private String  content;//	varchar	回复内容
    private String img;//	varchar	回复图片
}
