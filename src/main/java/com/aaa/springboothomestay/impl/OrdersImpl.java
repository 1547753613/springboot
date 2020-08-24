package com.aaa.springboothomestay.impl;

import com.aaa.springboothomestay.dao.OrderDao;
import com.aaa.springboothomestay.entity.Orders;
import com.aaa.springboothomestay.impl.service.OrdersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrdersImpl implements OrdersService {

    @Resource
    OrderDao orderDao;


    @Override
    public List<Orders> findAllOrders() {
        return orderDao.findAllOrders();
    }
}
