package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/collect")
public class CollectController {

    @Autowired
    CollectService collectService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, Integer userId, Integer valueId, String sort, String order){
        BaseData baseData = collectService.queryCollects(page, limit, userId, valueId, sort, order);
        return BaseRespVo.ok(baseData);
    }

}
