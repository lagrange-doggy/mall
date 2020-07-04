package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Region;
import com.cskaoyan.mall.service.WxRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("wx/region")
public class WxRegionController {

    @Autowired
    WxRegionService wxRegionService;

    @RequestMapping("list")
    public BaseRespVo list(Integer pid) {
        List<Region> regionData = wxRegionService.queryRegions(pid);
        return BaseRespVo.ok(regionData);
    }
}
