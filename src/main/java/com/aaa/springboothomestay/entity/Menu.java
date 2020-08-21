package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class Menu {
    private int mid;//	int	主键id
    private int url;//	varchar	后台访问路径
   private String  menu;//	varchar	前台路由路径
    private String icon;//	varchar	图标
    private String name;//	varchar	名字
    private int parentid;//	int	父类id

}
