package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.WxFootprintListVo;

import java.util.List;

public interface FootprintService {
    BaseData queryFoots(Integer page, Integer limit, Integer userId, Integer goodsId, String sort, String order);
    WxFootprintListVo queryFootsOnWx(Integer page, Integer size, Integer userId);

    void deleteFoot(Integer id);

    void insertFootprint(Integer userId ,Integer goodsId);
}
