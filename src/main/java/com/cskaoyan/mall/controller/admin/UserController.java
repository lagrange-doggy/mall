package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit,String username,String mobile ,String sort, String order){
        BaseData baseData = userService.queryUsers(page, limit,username,mobile, sort, order);
        return BaseRespVo.ok(baseData);
    }
}
