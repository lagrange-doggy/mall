package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.vo.*;
import com.cskaoyan.mall.mapper.RoleMapper;
import com.cskaoyan.mall.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public Role[] selectRole() {
        return roleMapper.selectRole();
//        return new AdminTypeVo[0];
    }

    @Override
    public BaseData queryUsers(Integer page, Integer limit, String sort, String order, String name) {

        RoleExample roleExample = new RoleExample();
        roleExample.setOrderByClause(sort + " " + order);
        //如果你当前的查询有条件，就增加criteria
        if (name != null) {
            roleExample.createCriteria().andNameLike("%" + name + "%");
        } else {
            roleExample.createCriteria();
        }
        //执行查询之前使用分页
        PageHelper.startPage(page, limit);
        List<Role> roles = roleMapper.selectByExample(roleExample);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        long total = pageInfo.getTotal();
        List<RoleListVo> roleListVoList = new ArrayList<>();
        for (Role role : roles) {
            roleListVoList.add(new RoleListVo(role.getId(), role.getName(), role.getEnabled(), role.getAddTime().toString(), role.getUpdateTime().toString(), role.getDeleted(), role.getDesc()));
        }
        return new BaseData(roleListVoList, total);
    }

    @Override
    public Role insertRole(String name, String desc) {
        Role role = new Role(null, name, desc, true,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()),
                false);
        roleMapper.insertSelective(role);
        return roleMapper.selectByPrimaryKey(role.getId());
    }

    @Override
    public void deleteRoleById(Integer id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateNameAndDescById(int id, String name, String desc) {
        roleMapper.updateNameAndDescById(id, name, desc);
    }

    @Override
    public MenuListVo queryMenu(Integer roleId) {
        List<Menu> menus = roleMapper.queryMenu();
        List<TopMenuVo> topMenuVos = new ArrayList<>();
        HashSet<Integer> flag = new HashSet<>();
        for (int i = 0; i < menus.size(); i++) {
            Menu menu = menus.get(i);
            if (menu.getParentId() == 0) {
                TopMenuVo topMenuVo = new TopMenuVo(new ArrayList<>(), menu.getName(), menu.getLabel());
                topMenuVos.add(topMenuVo);
                flag.add(i);
                for (int j = 0; j < menus.size(); j++) {
                    if (flag.contains(j)) {
                        continue;
                    }
                    Menu menu2 = menus.get(j);
                    if (StringUtil.isEmpty(menu2.getApi())) {
                        if (menu2.getParentId().equals(menu.getId())) {
                            FatherMenuVo fatherMenuVo = new FatherMenuVo(new ArrayList<>(), menu2.getName(), menu2.getLabel());
                            topMenuVo.getChildren().add(fatherMenuVo);
                            flag.add(j);
                            for (int k = 0; k < menus.size(); k++) {
                                if (flag.contains(k)) {
                                    continue;
                                }
                                Menu menu3 = menus.get(k);
                                if (menu3.getParentId().equals(menu2.getId())) {
                                    SonMenuVo sonMenuVo = new SonMenuVo(menu3.getApi(), menu3.getName(), menu3.getLabel());
                                    fatherMenuVo.getChildren().add(sonMenuVo);
                                    flag.add(k);
                                }
                            }
                        }
                    }
                }
            }
        }
        MenuListVo menuListVo = new MenuListVo();
        menuListVo.setSystemPermissions(topMenuVos);
        List<String> strings = roleMapper.queryPermissionById(roleId);
        if (strings.get(0).equals("*")) {
            strings = roleMapper.queryPermission();
        }
        menuListVo.setAssignedPermissions(strings);


        return menuListVo;
    }

    @Override
    public void updatePermissionById(Integer id, List<String> permissions) {
        List<String> strings = roleMapper.queryPermissionById(id);
        List<String> stringsTemp = new ArrayList<>(strings);
        strings.removeAll(permissions);
        if (strings .size()!=0) {
            roleMapper.deletePermissionsById(id, strings);
        }
        stringsTemp.retainAll(permissions);
        permissions.removeAll(stringsTemp);
        for (String s : stringsTemp) {
            roleMapper.updatePermissionByRoleIdAndPermission(id,s);
        }
        for (String permission : permissions) {
            roleMapper.insertPermissionByRoleIdAndPermission(id, permission);
        }
//        roleMapper.insertPermissionsById(id, permissions);
    }
}
