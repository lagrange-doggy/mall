package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.vo.OrderRefundVO;
import com.cskaoyan.mall.bean.vo.OrderShipVO;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Map<String, Object> orderPageList(Integer page, Integer limit, List<Integer> orderStatusArray, String sort, String order, Integer userId, String orderSn);

    Map<String, Object> queryOrderDetailById(Integer id);

    void orderShip(OrderShipVO shipVO);

    void orderRefund(OrderRefundVO orderRefundVO);

    Integer queryOrderNumByUserIdAndOrderStatus(Integer id, int orderStatus);
}