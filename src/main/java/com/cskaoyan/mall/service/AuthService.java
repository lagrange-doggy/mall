package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.bean.Permission;
import com.cskaoyan.mall.bean.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface AuthService {
    String queryPasswordByUsername(String username);

    int createAdminLog(String username, String action, Integer type, Boolean status, String result);

    List<String> selectPermissionByUsername(String username);

    Map selectInfoByName(Admin principal);

    Admin selectByUsername(String username);

    List<Role> selectRolesByAdminId(Integer id);


    List<Permission> selectPermissionByRoleId(Integer id);
}
