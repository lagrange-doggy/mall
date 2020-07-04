package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.bean.vo.WxFootprintListVo;
import com.cskaoyan.mall.service.FootprintService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wx/footprint")
public class WxFootprintController {
    @Autowired
    FootprintService footprintService;

    @GetMapping("list")
    public BaseRespVo listFootprint(Integer page, Integer size) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        WxFootprintListVo wxFootprintListVo = footprintService.queryFootsOnWx(page, size, user.getId());
        return BaseRespVo.ok(wxFootprintListVo);
    }

    @RequestMapping("delete")
    public BaseRespVo deleteFootprint(Integer id) {
        footprintService.deleteFoot(id);
        return BaseRespVo.ok();
    }
}
