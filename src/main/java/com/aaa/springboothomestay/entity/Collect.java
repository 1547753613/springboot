package com.aaa.springboothomestay.entity;

import lombok.Data;

@Data
public class Collect {
    private int id;//	int	主键id
    private int uid;//	int	用户外键
    private int hid;//	int	房屋表外键

}
