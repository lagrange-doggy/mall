package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.bo.AdminCreateBo;
import com.cskaoyan.mall.bean.bo.AdminUpdateBo;
import com.cskaoyan.mall.bean.vo.AdminCreateVo;
import com.cskaoyan.mall.bean.vo.AdminUpdateVo;
import com.cskaoyan.mall.config.Log;
import com.cskaoyan.mall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("admin/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping("list")
    @ResponseBody
    public BaseRespVo adminList(Integer page,Integer limit,String sort,String order , String username) {
        BaseData baseData = adminService.queryUsers(page, limit, sort, order,username);
        return BaseRespVo.ok(baseData);
    }

    @Log
    @RequestMapping("create")
    public BaseRespVo adminCreate(@RequestBody AdminCreateBo adminCreateBo) {
        AdminCreateVo adminCreateVo =adminService.insertAdmin(adminCreateBo.getUsername(), adminCreateBo.getPassword(),adminCreateBo.getAvatar(), adminCreateBo.getRoleIds());
        return BaseRespVo.ok(adminCreateVo);
    }

    @RequestMapping("read")
    public String adminRead() {

        return null;
    }

    @Log
    @RequestMapping("update")
    public BaseRespVo adminUpdate(@RequestBody AdminUpdateBo adminUpdateBo) {
        AdminUpdateVo adminUpdateVo = adminService.updateAdminByAdminUpdateBo(adminUpdateBo);
        return BaseRespVo.ok(adminUpdateVo);
    }

    @Log
    @RequestMapping("delete")
    public BaseRespVo adminDelete(@RequestBody Map map) {
        adminService.delectAdminById(map.get("id"));
        return BaseRespVo.ok();
    }
}
