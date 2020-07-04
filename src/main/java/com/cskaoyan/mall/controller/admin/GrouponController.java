package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.GrouponRules;
import com.cskaoyan.mall.service.GrouponService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/26 22:25
 */
@RestController
@RequestMapping("admin/groupon")
public class GrouponController {

    @Autowired
    GrouponService grouponService;

    @RequiresPermissions(value = "admin:groupon:list")
    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit,Integer goodsId, String sort, String order){
        BaseData baseData = grouponService.queryGrouponRules(page,limit,goodsId,sort,order);
        return BaseRespVo.ok(baseData);
    }

    @RequiresPermissions(value = "admin:groupon:create")
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody GrouponRules grouponRules) {
        grouponService.createGrouponRule(grouponRules);
        return BaseRespVo.ok(grouponRules);
    }

    @RequiresPermissions(value = "admin:groupon:delete")
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody GrouponRules grouponRules) {
        grouponService.deleteGrouponRule(grouponRules);
        return BaseRespVo.ok();
    }

    @RequiresPermissions(value = "admin:groupon:update")
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody GrouponRules grouponRules) {
        grouponService.updateGrouponRule(grouponRules);
        return BaseRespVo.ok(grouponRules);
    }

    @RequestMapping("listRecord")
    public BaseRespVo listRecord(Integer page, Integer limit,Integer goodsId, String sort, String order){
        BaseData baseData = grouponService.queryGroupons(page,limit,goodsId,sort,order);
        return BaseRespVo.ok(baseData);
    }
}
