package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.bo.GoodsCommentBo;
import com.cskaoyan.mall.bean.vo.OrderStatus;
import com.cskaoyan.mall.bean.vo.UserOrderInfoVO;
import com.cskaoyan.mall.bean.vo.UserOrderListVO;
import com.cskaoyan.mall.mapper.CommentMapper;
import com.cskaoyan.mall.mapper.GoodsProductMapper;
import com.cskaoyan.mall.mapper.OrderMapper;
import com.cskaoyan.mall.mapper.OrdersGoodsMapper;
import com.cskaoyan.mall.service.WxOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxOrderServiceImpl implements WxOrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrdersGoodsMapper orderGoodsMapper;

    @Autowired
    GoodsProductMapper goodsProductMapper;

    @Autowired
    CommentMapper commentMapper;


    @Override
    public Map queryOrders(Integer showType, Integer page, Integer size, Integer userId) {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        List orderStatus = OrderStatus.getOrderStatus(showType);
        if (orderStatus != null) {
            criteria.andOrderStatusIn(orderStatus);
        }
        criteria.andUserIdEqualTo(userId).andDeletedEqualTo(false);
        orderExample.setOrderByClause("update_time desc");
        PageHelper.startPage(page,size);
        List<UserOrderListVO> userOrderListVOS = orderMapper.selectOrderInfoByExample(orderExample);
        Long total = new PageInfo<>(userOrderListVOS).getTotal();
        Map userOrderData = new HashMap();
        userOrderData.put("count",total);
        userOrderData.put("totalPages",total / 10);
        if (total != 0) {
            userOrderData.replace("totalPages",total / 10 + 1);
            for (UserOrderListVO userOrderListVO : userOrderListVOS) {
                short status = userOrderListVO.getOrderStatus();
                userOrderListVO.setHandleOption(HandleOption.getInstance(status));
                userOrderListVO.setOrderStatusText(OrderStatus.getOrderStatusText(status));
            }
        }
        userOrderData.put("data",userOrderListVOS);
        return userOrderData;
    }


    @Override
    public Map queryOrderInfo(Integer orderId) {
        OrdersGoodsExample orderGoodsExample = new OrdersGoodsExample();
        orderGoodsExample.createCriteria().andOrderIdEqualTo(orderId);
        List<OrdersGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        UserOrderInfoVO userOrderInfoVO = orderMapper.selectOrderInfoByPrimaryKey(orderId);
        short status = userOrderInfoVO.getOrderStatus();
        userOrderInfoVO.setHandleOption(HandleOption.getInstance(status));
        userOrderInfoVO.setOrderStatusText(OrderStatus.getOrderStatusText(status));
        Map userOrderInfoData = new HashMap();
        userOrderInfoData.put("orderGoods",orderGoods);
        userOrderInfoData.put("orderInfo",userOrderInfoVO);
        return userOrderInfoData;
    }


    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public int cancelOrder(Integer orderId, short status) {
        Order order = new Order();
        order.setOrderStatus(status);
        order.setUpdateTime(new Date());
        int update = updateOrder(orderId, order);
        OrdersGoodsExample orderGoodsExample = new OrdersGoodsExample();
        orderGoodsExample.createCriteria().andOrderIdEqualTo(orderId);
        List<OrdersGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        for (OrdersGoods orderGood : orderGoods) {
            GoodsProductExample goodsProductExample = new GoodsProductExample();
            goodsProductExample.createCriteria().andIdEqualTo(orderGood.getProductId());

            int totalNum = goodsProductMapper.selectProductNumByExample(goodsProductExample);
            GoodsProduct goodsProduct = new GoodsProduct();
            goodsProduct.setNumber((orderGood.getNumber() + totalNum) + "");

            goodsProductMapper.updateByExampleSelective(goodsProduct,goodsProductExample);
        }
        return update;
    }


    @Override
    public int refund(Integer orderId, short status) {
        return cancelOrder(orderId,status);
    }

    @Override
    public int confirm(Integer orderId, short status) {
        Order order = new Order();
        order.setOrderStatus(status);
        order.setUpdateTime(new Date());
        order.setConfirmTime(new Date());
        return updateOrder(orderId, order);
    }


    @Override
    public OrdersGoods queryOrderGoodsInfo(Integer orderId, Integer goodsId) {
        OrdersGoodsExample orderGoodsExample = new OrdersGoodsExample();
        orderGoodsExample.createCriteria().andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId);
        List<OrdersGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        return orderGoods.get(0);
    }

        @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public int deleteOrder(Integer orderId) {
        OrdersGoods orderGoods = new OrdersGoods();
        orderGoods.setDeleted(true);
        OrdersGoodsExample orderGoodsExample = new OrdersGoodsExample();
        orderGoodsExample.createCriteria().andOrderIdEqualTo(orderId);
        orderGoodsMapper.updateByExampleSelective(orderGoods,orderGoodsExample);
        Order order = new Order();
        order.setDeleted(true);
        order.setUpdateTime(new Date());
        return updateOrder(orderId, order);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public int createGoodsComment(GoodsCommentBo goodsCommentBo) {
        Comment comment = new Comment();
        comment.setValueId(orderGoodsMapper.selectByPrimaryKey(goodsCommentBo.getOrderGoodsId()).getGoodsId());
        comment.setContent(goodsCommentBo.getContent());
        comment.setUserId(goodsCommentBo.getUserId());
        comment.setHasPicture(goodsCommentBo.getHasPicture());
        comment.setPicUrls(goodsCommentBo.getPicUrls());
        comment.setStar(goodsCommentBo.getStar());
        comment.setAddTime(new Date());
        comment.setUpdateTime(new Date());
        commentMapper.insertSelective(comment);

        OrdersGoods ordersGoods = new OrdersGoods();
        ordersGoods.setComment(comment.getId());
        ordersGoods.setUpdateTime(new Date());
        ordersGoods.setId(goodsCommentBo.getOrderGoodsId());
        orderGoodsMapper.updateByPrimaryKeySelective(ordersGoods);

        Order order = new Order();
        order.setOrderStatus((short)103);
        order.setUpdateTime(new Date());
        Integer orderId = goodsCommentBo.getOrderGoodsId();
        return updateOrder(orderId, order);
    }

    @Override
    public int prepayOrder(Integer orderId, short status) {
        Order order = new Order();
        order.setOrderStatus(status);
        order.setUpdateTime(new Date());
        order.setPayTime(new Date());
        return updateOrder(orderId, order);
    }

    public int updateOrder(Integer orderId, Order order) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andIdEqualTo(orderId);
        return orderMapper.updateByExampleSelective(order,orderExample);
    }

}