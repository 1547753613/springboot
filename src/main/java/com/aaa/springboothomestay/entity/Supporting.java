package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class Supporting {
    private int id;//	int 	主键id
    private String sname;//	varchar	配套设施
    private int parentid;//	int 	父id
    private String icon;//	varchar	图标

}
