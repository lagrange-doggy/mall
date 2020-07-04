package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/history")
public class HistoryController {
    @Autowired
    HistoryService historyService;
    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, Integer userId, String keyword, String sort, String order){
        BaseData baseData = historyService.searchHistory(page, limit, userId, keyword, sort, order);
        return BaseRespVo.ok(baseData);
    }
}
