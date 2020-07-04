package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.Ad;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.service.AdService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/26 14:24
 */
@RestController
@RequestMapping("admin/ad")
public class AdController {

    @Autowired
    AdService adService;

    @RequiresPermissions(value = "admin:ad:list")
    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit,String name, String content, String sort, String order){
        BaseData baseData = adService.queryAds(page,limit,name,content,sort,order);
        return BaseRespVo.ok(baseData);
    }

    @RequiresPermissions(value = "admin:ad:create")
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody Ad ad) {
        adService.createAd(ad);
        return BaseRespVo.ok();
    }

    @RequiresPermissions(value = "admin:ad:update")
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody Ad ad) {
        adService.updateAd(ad);
        return BaseRespVo.ok(ad);
    }

    @RequiresPermissions(value = "admin:ad:delete")
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Ad ad) {
        adService.deleteAd(ad);
        return BaseRespVo.ok();
    }
}
