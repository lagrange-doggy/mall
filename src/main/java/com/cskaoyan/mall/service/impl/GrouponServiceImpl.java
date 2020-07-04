package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.mapper.GrouponMapper;
import com.cskaoyan.mall.mapper.GrouponRulesMapper;
import com.cskaoyan.mall.service.GrouponService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/26 22:27
 */
@Service
public class GrouponServiceImpl implements GrouponService {

    @Autowired
    GrouponMapper grouponMapper;

    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public BaseData queryGroupons(Integer page, Integer limit, Integer goodsId, String sort, String order) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        GrouponRules rules = null;
        Goods goods = null;
        String subGroupons = null;
        GrouponExample grouponExample = new GrouponExample();
        grouponExample.setOrderByClause(sort + " " + order);
        List<Groupon> groupons = grouponMapper.selectByExample(grouponExample);
        for (Groupon groupon : groupons) {
            HashMap<String, Object> map = new HashMap<>();
            int i = grouponMapper.countUserById(groupon.getCreatorUserId());
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 1; j <= i; j++) {
                stringBuilder.append(i);
            }
            subGroupons = stringBuilder.toString();
            rules = grouponRulesMapper.selectByPrimaryKey(groupon.getRulesId());
            goods = goodsMapper.selectByPrimaryKey(goodsId);
            map.put("groupon", groupon);
            map.put("goods", goods);
            map.put("rules", rules);
            map.put("subGroupons", subGroupons);
            if (goodsId == null) list.add(map);
            else {
                if (rules.getGoodsId().equals(goodsId)) list.add(map);
            }
        }
        PageHelper.startPage(page, limit);
        PageInfo<HashMap<String, Object>> pageInfo = new PageInfo<>();
        long total = pageInfo.getTotal();
        return new BaseData(list, total);
    }

    @Override
    public void deleteGrouponRule(GrouponRules grouponRules) {
        grouponRulesMapper.deleteByPrimaryKey(grouponRules.getId());
    }

    @Override
    public void updateGrouponRule(GrouponRules grouponRules) {
        grouponRules.setUpdateTime(new Date());
        grouponRulesMapper.updateByPrimaryKey(grouponRules);
    }

    @Override
    public void createGrouponRule(GrouponRules grouponRules) {
        Date date = new Date();
        grouponRules.setUpdateTime(date);
        grouponRules.setAddTime(date);
        grouponRules.setDeleted(false);
        Goods goods = goodsMapper.selectByPrimaryKey(grouponRules.getGoodsId());
        grouponRules.setGoodsName(goods.getName());
        grouponRules.setPicUrl(goods.getPicUrl());
        grouponRulesMapper.insert(grouponRules);
    }

    @Override
    public BaseData queryGrouponRules(Integer page, Integer limit, Integer goodsId, String sort, String order) {
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        grouponRulesExample.setOrderByClause(sort + " " + order);
        if (goodsId != null) grouponRulesExample.createCriteria().andGoodsIdEqualTo(goodsId);
        PageHelper.startPage(page,limit);
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(grouponRulesExample);
        PageInfo<GrouponRules> pageInfo = new PageInfo<>(grouponRules);
        long total = pageInfo.getTotal();
        return new BaseData(grouponRules, total);
    }

}
