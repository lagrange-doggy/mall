package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.mapper.GoodsProductMapper;
import com.cskaoyan.mall.mapper.OrderMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ZhidaFeng on 2020/6/30
 **/
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodsProductMapper goodsProductMapper;
    @Override
    public Dashboard queryDashboard() {
        Dashboard dashboard = new Dashboard();
        long goods = goodsMapper.countByExample(new GoodsExample());
        long users = userMapper.countByExample(new UserExample());
        long orders = orderMapper.countByExample(new OrderExample());
        long product = goodsProductMapper.countByExample(new GoodsProductExample());
        dashboard.setGoodsTotal(goods);
        dashboard.setUserTotal(users);
        dashboard.setOrderTotal(orders);
        dashboard.setProductTotal(product);

        return dashboard;
    }
}
