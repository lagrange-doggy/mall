package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.OrdersGoods;
import com.cskaoyan.mall.bean.bo.GoodsCommentBo;

import java.util.Map;

public interface WxOrderService {
    Map queryOrders(Integer showType, Integer page, Integer size, Integer userId);

    Map queryOrderInfo(Integer orderId);

    int cancelOrder(Integer orderId, short status);

    int refund(Integer orderId, short status);

    int confirm(Integer orderId, short status);

    OrdersGoods queryOrderGoodsInfo(Integer orderId, Integer goodsId);

    int deleteOrder(Integer orderId);

    int createGoodsComment(GoodsCommentBo goodsCommentBo);

    int prepayOrder(Integer orderId, short status);
}