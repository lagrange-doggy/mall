package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.GoodsStatistics;
import com.cskaoyan.mall.bean.OrderStistiscs;
import com.cskaoyan.mall.bean.UserStatistics;
import com.cskaoyan.mall.bean.vo.StatisticalVo;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.mapper.OrderMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ZhidaFeng on 2020/6/27
 **/
@Service
public class StatisticalServiceImpl implements StatisticalService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public StatisticalVo<UserStatistics> getUserStatistical() {

        StatisticalVo<UserStatistics> userStatisticsStatisticalVo = new StatisticalVo<>();
        List columns = new ArrayList();
        columns.add("day");
        columns.add("users");
        userStatisticsStatisticalVo.setColumns(columns);

        List<UserStatistics> userStatistics = userMapper.selectGroupByAddTime();
        userStatisticsStatisticalVo.setRows(userStatistics);
        return userStatisticsStatisticalVo;
    }

    @Override
    public StatisticalVo<OrderStistiscs> getOrderStatistical() {

        StatisticalVo<OrderStistiscs> orderStistiscsStatisticalVo = new StatisticalVo<>();
        List columns = new ArrayList();
        columns.add("day");
        columns.add("orders");
        columns.add("customers");
        columns.add("amount");
        columns.add("pcr");
        orderStistiscsStatisticalVo.setColumns(columns);

        List<OrderStistiscs> orderStistiscs = orderMapper.selectGroupByOrder();

        orderStistiscsStatisticalVo.setRows(orderStistiscs);
        return orderStistiscsStatisticalVo;
    }

    @Override
    public StatisticalVo<GoodsStatistics> getGoodsStatical() {
        StatisticalVo<GoodsStatistics> goodsStatisticsStatisticalVo = new StatisticalVo<>();
        List columns = new ArrayList();
        columns.add("day");
        columns.add("orders");
        columns.add("products");
        columns.add("amount");
        goodsStatisticsStatisticalVo.setColumns(columns);

        List<GoodsStatistics> goodsStatistics = goodsMapper.selectGroupByGoods();
        goodsStatisticsStatisticalVo.setRows(goodsStatistics);
        return goodsStatisticsStatisticalVo;
    }
}
