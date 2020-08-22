package com.aaa.springboothomestay.impl.service;

import com.aaa.springboothomestay.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface MenuService {

    /**
     *
     * @param sets 查询访问权限集合
     * @return
     */
    public List<Menu> SelectMenuId(List<Integer> sets);

    /**
     *
     * @param id 查询二级菜单
     * @return
     */
    public List<Menu> SelectMenuParentId(int id);
}
