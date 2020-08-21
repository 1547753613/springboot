package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class Role {
    private int rid;//角色id
    private String rname;//	角色
    private  String role;//	角色名称
    private int did;//外键部门
}
