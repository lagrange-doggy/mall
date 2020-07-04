package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/log")
public class LogController {
    @Autowired
    LogService logService;

    @RequestMapping("list")
    @ResponseBody
    public BaseRespVo roleList(Integer page, Integer limit, String sort, String order, String name) {
        BaseData baseData = logService.queryUsers(page, limit, sort, order, name);
        return BaseRespVo.ok(baseData);
    }
}
