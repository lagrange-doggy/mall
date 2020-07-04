package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.vo.OrderRefundVO;
import com.cskaoyan.mall.bean.vo.OrderShipVO;
import com.cskaoyan.mall.constants.OrderStatusConstants;
import com.cskaoyan.mall.mapper.OrderMapper;
import com.cskaoyan.mall.mapper.OrdersGoodsMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrdersGoodsMapper ordersGoodsMapper;


    @Override
    public Map<String, Object> orderPageList(Integer page, Integer limit, List<Integer> orderStatusArray, String sort, String order, Integer userId, String orderSn) {

        PageHelper.startPage(page, limit);
        List<Order> orders = orderMapper.selectOrderPageList(orderStatusArray, sort, order, userId, orderSn);
        PageInfo orderPage = new PageInfo(orders);
        long total = orderPage.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", orders);
        return map;
    }

    @Override
    public Map<String, Object> queryOrderDetailById(Integer id) {
        Map<String, Object> map = new HashMap<>();
        //查询订单表信息
        Order order = orderMapper.selectByPrimaryKey(id);
        //查询用户信息
        User user = userMapper.selectByPrimaryKey(order.getUserId());
        //查询中间表信息
        OrdersGoodsExample ordersGoodsExample = new OrdersGoodsExample();
        ordersGoodsExample.createCriteria().andOrderIdEqualTo(id);
        List<OrdersGoods> orderGoods = ordersGoodsMapper.selectByExample(ordersGoodsExample);
        map.put("user", user);
        map.put("orderGoods", orderGoods);
        map.put("order", order);
        return map;
    }

    @Override
    public void orderShip(OrderShipVO shipVO) {
        Order order = orderMapper.selectByPrimaryKey(shipVO.getOrderId());
        order.setShipChannel(shipVO.getShipChannel());
        order.setShipSn(shipVO.getShipSn());
        order.setUpdateTime(new Date());
        order.setOrderStatus(OrderStatusConstants.ORDER_SHIPPED);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void orderRefund(OrderRefundVO orderRefundVO) {
        Order order = orderMapper.selectByPrimaryKey(orderRefundVO.getOrderId());
        order.setUpdateTime(new Date());
        order.setOrderStatus(OrderStatusConstants.ORDER_REFUNDED);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Integer queryOrderNumByUserIdAndOrderStatus(Integer id, int orderStatus) {
        return orderMapper.queryOrderNumByUserIdAndOrderStatus(id,orderStatus);
    }
}