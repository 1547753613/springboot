package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class MenuRole {
    private int id;//	int	主键id
    private int  mid;//	int	外键权限
    private int rid;//	int	外键角色
}
