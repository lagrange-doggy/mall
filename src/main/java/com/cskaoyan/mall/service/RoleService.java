package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Role;
import com.cskaoyan.mall.bean.vo.MenuListVo;

import java.util.List;

public interface RoleService {
    Role[] selectRole();

    BaseData queryUsers(Integer page, Integer limit, String sort, String order, String name);

    Role insertRole(String name, String desc);

    void deleteRoleById(Integer id);

    void updateNameAndDescById(int id, String name, String  desc);

    MenuListVo queryMenu(Integer roleId);

    void updatePermissionById(Integer id, List<String> permissions);
}
