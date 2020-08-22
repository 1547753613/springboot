package com.aaa.springboothomestay.impl;

import com.aaa.springboothomestay.dao.MenuDao;
import com.aaa.springboothomestay.entity.Menu;
import com.aaa.springboothomestay.impl.service.MenuService;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Repository
public class MenuImpl implements MenuService {

    @Resource
    MenuDao menuDao;
    /**
     *
     * @param sets 查询访问权限集合
     * @return
     */
    @Override
    public List<Menu> SelectMenuId(Set<Integer> sets) {

        Example example=new Example(Menu.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("mid",sets);
        List<Menu> menus = menuDao.selectByExample(example);

        return menus;
    }
}
