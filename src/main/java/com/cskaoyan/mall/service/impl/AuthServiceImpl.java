package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.AdminMapper;
import com.cskaoyan.mall.mapper.LogMapper;
import com.cskaoyan.mall.mapper.PermissionMapper;
import com.cskaoyan.mall.mapper.RoleMapper;
import com.cskaoyan.mall.service.AuthService;
import com.cskaoyan.mall.utils.ConvertUtils;
import com.cskaoyan.mall.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @ZhidaFeng on 2020/6/29
 **/
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    HttpServletRequest request;

    @Autowired
    LogMapper logMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public String queryPasswordByUsername(String username) {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andUsernameEqualTo(username);
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        return admins.size() >= 1 ? admins.get(0).getPassword() : null;
    }

    @Override
    public int createAdminLog(String username, String action, Integer type, Boolean status, String result) {
        Log log = new Log();
        log.setAdmin(username);
        //获取登录的IP
        String ipAddr = IpUtils.getIpAddr(request);
        log.setIp(ipAddr);
        log.setType(type);
        log.setStatus(status);
        log.setAction(action);
        log.setResult(result);
        log.setAddTime(new Date());
        log.setUpdateTime(new Date());
        log.setDeleted(false);
        int i = logMapper.insertSelective(log);
        return i;
    }

    @Override
    public List<String> selectPermissionByUsername(String username) {
        Admin admin = selectAdminByUsername(username);

        List<Integer> roleIdList = ConvertUtils.string2List(admin.getRoleIds());
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andRoleIdIn(roleIdList);
        List<Permission> permissionObjectList = permissionMapper.selectByExample(permissionExample);
        ArrayList<String> permissionList = new ArrayList<>();
        for (Permission p : permissionObjectList) {
            permissionList.add(p.getPermission());
        }
        return permissionList;
    }

    @Override
    public List<Permission> selectPermissionByRoleId(Integer roleId) {
        return permissionMapper.selectByRoleId(roleId);
    }

    @Override
    public List<Role> selectRolesByAdminId(Integer id) {
        ArrayList<Role> roles = new ArrayList<>();
        List list = ConvertUtils.string2List(adminMapper.selectRolesIdById(id));
        for (Object roleId : list) {
            Role role = roleMapper.selectByPrimaryKey((Integer) roleId);
            roles.add(role);
        }
        return roles;
    }

    @Override
    public Admin selectByUsername(String username) {
        return adminMapper.selectByUsername(username);
    }

    @Override
    public Map selectInfoByName(Admin adminbo) {
        Admin admin = selectAdminByUsername(adminbo.getUsername());

        List<String> permissionList = selectPermissionByUsername(adminbo.getUsername());

        List roleIds = ConvertUtils.string2List(admin.getRoleIds());
        ArrayList roleIdList = new ArrayList<>(roleIds.size());
        Collections.addAll(roleIdList, roleIds);

        HashMap<String, Object> map = new HashMap<>();
        map.put("roles", selectRoleNamesByRoleIds(roleIdList));
        map.put("name", adminbo.getUsername());
        map.put("perms", changePermissionListFormat(permissionList));
        map.put("avatar", admin.getAvatar());
        return map;
    }

    public List<String> changePermissionListFormat(List<String> permissionList) {
        ArrayList<String> resultList = new ArrayList<>();
        String r = null;
        for (String s : permissionList) {
            if ("*".equals(s)) {
                r = s;
            } else {
                String replace = s.replace(":", "/");

                if (replace.endsWith("read") || replace.endsWith("list")) {
                    r = "GET /" + replace;
                } else {
                    r = "POST /" + replace;
                }
            }
            resultList.add(r);
        }
        return resultList;
    }

    public List<String> selectRoleNamesByRoleIds(List roleIds) {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andIdIn(roleIds);
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        ArrayList<String> roleNames = new ArrayList<>();
        for (Role r : roleList) {
            roleNames.add(r.getName());
        }
        return roleNames;
    }

    private Admin selectAdminByUsername(String username) {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andUsernameEqualTo(username);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        return admins.size() >= 1 ? admins.get(0) : null;
    }

}
