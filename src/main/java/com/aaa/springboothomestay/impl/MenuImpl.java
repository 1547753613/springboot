package com.aaa.springboothomestay.impl;

import com.aaa.springboothomestay.dao.MenuDao;
import com.aaa.springboothomestay.entity.Menu;
import com.aaa.springboothomestay.impl.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Menu> SelectMenuId(List<Integer> sets) {

        Example example=new Example(Menu.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("mid",sets);
        List<Menu> menus = menuDao.selectByExample(example);
        int i=0;
        for (Menu menu:menus){
            if (null!=menu.getParentid()&&menu.getParentid()!=0){
                List<Menu> menuList = this.SelectMenuParentId(menu.getMid());
                menus.get(i).setChildren(menuList);

            }
            i++;

        }

        return menus;
    }

    /**
     *
     * @param id 查询二级菜单
     * @return
     */
    @Override
    public List<Menu> SelectMenuParentId(int id) {
        Menu menu=new Menu();
        menu.setParentid(id);
        List<Menu> menus = menuDao.select(menu);
        return menus;
    }
}
