package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class Unsubscribe {
    private int id;//	int 	主键id
    private String uname;//	varchar	退订规则表
    private String uvarchar;//	varchar	退订说明
    private String uimage;//	varchar	图片实例
}
