package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class Bedtype {
    private int bid;//	int 	主键id
    private String bame;//	varchar	床型(双人床，单人床...)
    private int parentid;//	int 	父id

}
