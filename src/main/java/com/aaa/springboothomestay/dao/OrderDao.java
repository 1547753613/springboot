package com.aaa.springboothomestay.dao;

import com.aaa.springboothomestay.entity.Orders;

import java.util.List;

public interface OrderDao {

    List<Orders> findAllOrders();
}
