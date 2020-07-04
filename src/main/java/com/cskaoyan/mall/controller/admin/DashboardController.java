package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Dashboard;
import com.cskaoyan.mall.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ZhidaFeng on 2020/6/29
 **/
@RestController
@RequestMapping("admin")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @RequestMapping(value = "dashboard",method = RequestMethod.GET)
    public BaseRespVo dashboard(){
        Dashboard dashboard = dashboardService.queryDashboard();
        return BaseRespVo.ok(dashboard);
    }
}
