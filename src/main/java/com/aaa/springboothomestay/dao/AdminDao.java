package com.aaa.springboothomestay.dao;

import com.aaa.springboothomestay.entity.Admins;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
@org.apache.ibatis.annotations.Mapper
public interface AdminDao extends Mapper<Admins>, MySqlMapper<Admins> {
}
