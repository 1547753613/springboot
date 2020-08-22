package com.aaa.springboothomestay.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "role")
@Data
public class Role {
    @Id
    private Integer rid;//角色id
    @Column
    private String rname;//	角色
    @Column
    private  String role;//	角色名称

    @Column
    private Integer did;//外键部门
}
