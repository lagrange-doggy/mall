package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.GoodsStatistics;
import com.cskaoyan.mall.bean.OrderStistiscs;
import com.cskaoyan.mall.bean.UserStatistics;
import com.cskaoyan.mall.bean.vo.StatisticalVo;

public interface StatisticalService {
    StatisticalVo<UserStatistics> getUserStatistical();

    StatisticalVo<OrderStistiscs> getOrderStatistical();

    StatisticalVo<GoodsStatistics> getGoodsStatical();
}
