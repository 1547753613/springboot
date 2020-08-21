package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class Reply {
    private int id;//	int	主键id
    private int eid;//int	评价表外键
    private int lid;//	int	商家表外键
    private String content;//	varchar	回复内容
}
