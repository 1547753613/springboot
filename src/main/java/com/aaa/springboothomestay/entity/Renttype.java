package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class Renttype {
    private int rid;//	int 	主键id
    private String tname;//	varchar	出租类型(整套，单间)
    private String icon;//	varchar	图标

}
