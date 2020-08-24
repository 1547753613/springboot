package com.aaa.springboothomestay.impl.service;

import com.aaa.springboothomestay.entity.Orders;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrdersService {
    @Select("select * from orders")
    List<Orders> findAllOrders();
}
