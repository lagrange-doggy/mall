package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.vo.AdminTypeVo;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.vo.MenuListVo;
import com.cskaoyan.mall.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @RequestMapping("options")
    public BaseRespVo roleOptions() {
        Role[] roles = roleService.selectRole();
        List<AdminTypeVo> adminTypeVos = new ArrayList<>();
        for (Role role : roles) {
            adminTypeVos.add(new AdminTypeVo(role.getId(), role.getName()));
        }
        return BaseRespVo.ok(adminTypeVos);
    }

    @RequestMapping("list")
    @ResponseBody
    public BaseRespVo roleList(Integer page, Integer limit, String sort, String order, String name) {
        BaseData baseData = roleService.queryUsers(page, limit, sort, order, name);
        return BaseRespVo.ok(baseData);
    }

    @GetMapping("permissions")
    public BaseRespVo rolePermissions1(Integer roleId) {
        MenuListVo menuListVo = roleService.queryMenu(roleId);
        return BaseRespVo.ok(menuListVo);
    }

    @PostMapping("permissions")
    public BaseRespVo rolePermissions2(@RequestBody Map map) {
        List<String> permissions = (List<String>) map.get("permissions");
        Integer id = (Integer) map.get("roleId");
        roleService.updatePermissionById(id,permissions);
        return BaseRespVo.ok();
    }

    @RequestMapping("create")
    @ResponseBody
    public BaseRespVo roleCreate(@RequestBody Map map) {
        Role role = roleService.insertRole((String) map.get("name"), (String) map.get("desc"));
        return BaseRespVo.ok(role);
    }

    @RequestMapping("delete")
    @ResponseBody
    public BaseRespVo roleDelete(@RequestBody Map map) {
        roleService.deleteRoleById((int) map.get("id"));
        return BaseRespVo.ok();
    }

    @RequestMapping("update")
    @ResponseBody
    public BaseRespVo roleUpdate(@RequestBody Map map) {
        roleService.updateNameAndDescById((int) map.get("id"), (String) map.get("name"), (String) map.get("desc"));
        return BaseRespVo.ok();
    }

}
