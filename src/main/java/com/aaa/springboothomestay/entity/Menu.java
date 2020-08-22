package com.aaa.springboothomestay.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@Table(name = "menu")
public class Menu {
    @Id
    private Integer mid;//	int	主键id
    private String url;//	varchar	后台访问路径
   private String  menu;//	varchar	前台路由路径
    private String icon;//	varchar	图标
    private String name;//	varchar	名字
    private Integer parentid;//	int	父类id

    private List<Menu> children;//子菜单

}
