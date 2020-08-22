package com.aaa.springboothomestay.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "menu_role")
public class MenuRole {
    @Id
    private Integer id;//	int	主键id
    @Column
    private Integer  mid;//	int	外键权限
    @Column
    private Integer rid;//	int	外键角色
}
