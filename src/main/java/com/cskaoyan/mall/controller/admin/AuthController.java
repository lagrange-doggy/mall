package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.bean.Permission;
import com.cskaoyan.mall.bean.Role;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.config.Log;
import com.cskaoyan.mall.service.AuthService;
import com.cskaoyan.mall.shiro.token.MallToken;
import com.cskaoyan.mall.utils.Md5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    HttpServletRequest request;

    /**
     * 整合shiro的时候自己增加业务逻辑
     *
     * @param map
     * @return
     */
    @Log
    @RequestMapping("login")
    public BaseRespVo login(@RequestBody Map map) {
        String username = (String) map.get("username");
        String password = Md5Utils.getMD5((String) map.get("password"));

        MallToken adminToken = new MallToken(username, password, "admin");
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(adminToken);
        } catch (AuthenticationException e) {
            // authService.createAdminLog(username, "登陆", 1, false, "用户名或密码错误");
            return BaseRespVo.err();
        }
        String id = (String) subject.getSession().getId();
        // authService.createAdminLog(username, "登陆", 1, true, "");
        return BaseRespVo.ok(id);
    }


    @RequestMapping("info")
    public BaseRespVo info(String token) {
        Subject subject = SecurityUtils.getSubject();
        Admin admin = (Admin) subject.getPrincipal();
        if (!token.equals(subject.getSession().getId())) return BaseRespVo.err();
        Map data = new HashMap<String, Object>();
        data.put("avatar", admin.getAvatar());
        data.put("name", admin.getUsername());
        ArrayList<String> rolesName = new ArrayList<>();
        ArrayList<String> permsName = new ArrayList<>();
        List<Role> roles = authService.selectRolesByAdminId(admin.getId());
        for (Role role : roles) {
            rolesName.add(role.getName());
            List<Permission> perms = authService.selectPermissionByRoleId(role.getId());
            for (Permission perm : perms) {
                String permission = perm.getPermission();
                if (!permission.equals("*")) {
                    String method = request.getMethod();
                    permission = ":" + permission;
                    permission = permission.replace(':', '/');
                    permission = method + " " + permission;
                }
                permsName.add(permission);
            }
        }
        data.put("perms", permsName);
        data.put("roles", rolesName);
        return BaseRespVo.ok(data);
    }

    @RequestMapping("logout")
    public BaseRespVo logout() {
        Subject subject = SecurityUtils.getSubject();
        Admin admin = (Admin) subject.getPrincipal();
        try {
            subject.logout();
        } catch (Exception e) {
            return BaseRespVo.err("请重新登录");
        }
        // authService.createAdminLog(admin.getUsername(), "退出", 1, true, "");
        return BaseRespVo.ok();
    }
}
