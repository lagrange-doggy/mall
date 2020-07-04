package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Footprint;
import com.cskaoyan.mall.bean.FootprintExample;
import com.cskaoyan.mall.bean.vo.FootprintVo;
import com.cskaoyan.mall.bean.vo.WxFootprintListVo;
import com.cskaoyan.mall.mapper.FootprintMapper;
import com.cskaoyan.mall.service.FootprintService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootprintServiceImpl implements FootprintService {
    @Autowired
    FootprintMapper footprintMapper;

    @Override
    public BaseData queryFoots(Integer page, Integer limit, Integer userId, Integer goodsId, String sort, String order) {
        FootprintExample footprintExample = new FootprintExample();
        footprintExample.setOrderByClause(sort + " " + order);

        if (userId != null && goodsId == null) {
            footprintExample.createCriteria().andUserIdEqualTo(userId);
        }
        if (userId == null && goodsId != null) {
            footprintExample.createCriteria().andGoodsIdEqualTo(goodsId);
        }
        if (userId != null && goodsId != null) {
            footprintExample.createCriteria().andUserIdEqualTo(userId).andGoodsIdEqualTo(goodsId);
        }

        PageHelper.startPage(page, limit);
        List<Footprint> footprints = footprintMapper.selectByExample(footprintExample);
        PageInfo<Footprint> pageInfo = new PageInfo<>(footprints);
        long total = pageInfo.getTotal();
        return new BaseData(footprints, total);
    }

    @Override
    public WxFootprintListVo queryFootsOnWx(Integer page, Integer size, Integer userId) {
        PageHelper.startPage(page, size);
        List<FootprintVo> footprints = footprintMapper.queryFootsOnWx(userId);
        PageInfo<FootprintVo> pageInfo = new PageInfo<>(footprints);
        long total = pageInfo.getTotal();
        return new WxFootprintListVo(footprints, (int) total);
    }

    @Override
    public void deleteFoot(Integer id) {
        footprintMapper.deleteFootprint(id);
    }

    @Override
    public void insertFootprint(Integer userId, Integer goodsId) {
        if (footprintMapper.selectFootprintByUserIdAndGoodsId(userId, goodsId) == null) {
            footprintMapper.insertFootprint(userId, goodsId);
        } else {
            footprintMapper.updateFootprint(userId, goodsId);
        }
    }
}
