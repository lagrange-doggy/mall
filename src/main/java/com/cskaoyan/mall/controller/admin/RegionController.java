package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Region;
import com.cskaoyan.mall.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin/region")
public class RegionController {

    @Autowired
    RegionService regionService;

    @RequestMapping("list")
    public BaseRespVo list() {
        List<Region> regionData = regionService.queryRegions();
        return BaseRespVo.ok(regionData);
    }
}