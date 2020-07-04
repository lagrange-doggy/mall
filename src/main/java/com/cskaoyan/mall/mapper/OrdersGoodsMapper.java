package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.OrdersGoods;
import com.cskaoyan.mall.bean.OrdersGoodsExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface OrdersGoodsMapper {
    int countByExample(OrdersGoodsExample example);

    int deleteByExample(OrdersGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrdersGoods record);

    int insertSelective(OrdersGoods record);

    List<OrdersGoods> selectByExample(OrdersGoodsExample example);

    OrdersGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrdersGoods record, @Param("example") OrdersGoodsExample example);

    int updateByExample(@Param("record") OrdersGoods record, @Param("example") OrdersGoodsExample example);

    int updateByPrimaryKeySelective(OrdersGoods record);

    int updateByPrimaryKey(OrdersGoods record);
}