package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.OrdersGoods;
import com.cskaoyan.mall.bean.bo.GoodsCommentBo;
import com.cskaoyan.mall.service.WxOrderService;
import com.cskaoyan.mall.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("wx/order")
public class WxOrderController {

    @Autowired
    WxOrderService wxOrderService;

    @RequestMapping("submit")
    public BaseRespVo submitOrder(@RequestBody Map<String,Integer> map) {
        return BaseRespVo.ok();
    }

    @RequestMapping("prepay")
    public BaseRespVo prepayOrder(@RequestBody Map<String,Integer> map) {
        int update = wxOrderService.prepayOrder(map.get("orderId"), (short) 201);
        update--;
        if (update != 1) {
            return BaseRespVo.err(404,"支付失败");
        }
        return BaseRespVo.ok();
    }

    @RequestMapping("list")
    public BaseRespVo queryOrderList(Integer showType, Integer page, Integer size) {
        Integer userId= UserUtil.getUserId();
        Map orderListData = wxOrderService.queryOrders(showType,page,size,userId);
        return BaseRespVo.ok(orderListData);
    }

    @RequestMapping("detail")
    public BaseRespVo queryOrderInfo(Integer orderId) {
        Map orderInfo = wxOrderService.queryOrderInfo(orderId);
        return BaseRespVo.ok(orderInfo);
    }

    @RequestMapping("cancel")
    public BaseRespVo cancelOrder(@RequestBody Map<String,Integer> map) {
        wxOrderService.cancelOrder(map.get("orderId"), (short) 102);
        return BaseRespVo.ok();
    }

    @RequestMapping("refund")
    public BaseRespVo refund(@RequestBody Map<String,Integer> map) {
        wxOrderService.refund(map.get("orderId"), (short) 202);
        return BaseRespVo.ok();
    }

    @RequestMapping("confirm")
    public BaseRespVo confirm(@RequestBody Map<String,Integer> map) {
        wxOrderService.confirm(map.get("orderId"), (short) 401);
        return BaseRespVo.ok();
    }

    @RequestMapping("goods")
    public BaseRespVo queryOrderGoodsInfo(Integer orderId, Integer goodsId) {
        OrdersGoods ordersGoods = wxOrderService.queryOrderGoodsInfo(orderId,goodsId);
        return BaseRespVo.ok(ordersGoods);
    }

    @RequestMapping("delete")
    public BaseRespVo deleteOrder(@RequestBody Map<String,Integer> map) {
        wxOrderService.deleteOrder(map.get("orderId"));
        return BaseRespVo.ok();
    }

    @RequestMapping("comment")
    public BaseRespVo createGoodsComment(@RequestBody GoodsCommentBo goodsCommentBo) {
        Integer userId= UserUtil.getUserId();
        goodsCommentBo.setUserId(userId);
        wxOrderService.createGoodsComment(goodsCommentBo);
        return BaseRespVo.ok();
    }
}