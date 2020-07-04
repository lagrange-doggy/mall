package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.vo.CollectVo;
import com.cskaoyan.mall.mapper.CollectMapper;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.CollectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    CollectMapper collectMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public Collect queruCollectByValueId(Integer valueId) {
        Collect collect = collectMapper.selectByValueIdExists(valueId);
        return collect;
    }

    @Override
    public void updateCollect(Integer type, Integer valueId) {
        Collect collect = collectMapper.selectByValueId(valueId);
        if (collect == null) {
            collect = new Collect();
            collect.setType(Byte.parseByte(type.toString()));
            collect.setValueId(valueId);
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            collect.setUserId(user.getId());
            collect.setAddTime(new Date());
            collect.setUpdateTime(new Date());
            collect.setDeleted(false);
            collectMapper.insert(collect);
        } else {
            collect.setUpdateTime(new Date());
            collect.setDeleted(!collect.getDeleted());
            collectMapper.updateByValueId(collect);
        }
    }

    @Override
    public Map<String, Object> queryCollects(Integer page, Integer size, Byte type) {
        HashMap<String, Object> map = new HashMap<>();
        CollectExample collectExample = new CollectExample();
        collectExample.createCriteria().andTypeEqualTo(type).andDeletedEqualTo(false);
        List<Collect> collects = collectMapper.selectByExample(collectExample);
        ArrayList<CollectVo> collectVos = new ArrayList<>();
        for (Collect collect : collects) {
            Goods goods = goodsMapper.selectByPrimaryKey(collect.getValueId());
            CollectVo collectVo = new CollectVo();
            collectVo.setBrief(goods.getBrief());
            collectVo.setPicUrl(goods.getPicUrl());
            collectVo.setValueId(goods.getId());
            collectVo.setName(goods.getName());
            collectVo.setId(collect.getId());
            collectVo.setType(collect.getType());
            collectVo.setRetailPrice(goods.getRetailPrice());
            collectVos.add(collectVo);
        }
        PageHelper.startPage(page, size);
        PageInfo<Collect> pageInfo = new PageInfo<>();
        long total = pageInfo.getTotal();
        map.put("totalPages", total);
        map.put("collectList", collectVos);
        return map;
    }

    @Override
    public BaseData queryCollects(Integer page, Integer limit, Integer userId, Integer valueId, String sort, String order) {
        CollectExample collectExample = new CollectExample();
        collectExample.setOrderByClause(sort + " " + order);

        if(userId != null && valueId == null){
            collectExample.createCriteria().andUserIdEqualTo(userId);
        }
        if(userId == null && valueId != null){
            collectExample.createCriteria().andValueIdEqualTo(valueId);
        }
        if(userId != null && valueId != null){
            collectExample.createCriteria().andUserIdEqualTo(userId).andValueIdEqualTo(valueId);
        }
        PageHelper.startPage(page,limit);
        List<Collect> collects = collectMapper.selectByExample(collectExample);
        PageInfo<Collect> pageInfo = new PageInfo<>(collects);
        long total = pageInfo.getTotal();
        return new BaseData(collects, total);
    }
}
