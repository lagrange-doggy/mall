package com.cskaoyan.mall.controller.admin;


import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.service.FootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/footprint")
public class FootprintController {

    @Autowired
    FootprintService footprintService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, Integer userId, Integer goodsId, String sort, String order){
        BaseData baseData = footprintService.queryFoots(page, limit, userId, goodsId, sort, order);
        return BaseRespVo.ok(baseData);
    }

}
