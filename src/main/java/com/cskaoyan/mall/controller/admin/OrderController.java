package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.vo.OrderRefundVO;
import com.cskaoyan.mall.bean.vo.OrderShipVO;
import com.cskaoyan.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/order")
//跨域
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("list")
    public BaseRespVo orderPageList(@RequestParam("page") Integer page,
                           @RequestParam("limit") Integer limit,
                           @RequestParam(value = "orderStatusArray",required = false) List<Integer> orderStatusArray,
                           @RequestParam("sort") String sort,
                           @RequestParam("order") String order,
                           @RequestParam(value = "userId",required = false) Integer userId,
                           @RequestParam(value = "orderSn",required = false) String orderSn){
        Map<String,Object> map= orderService.orderPageList(page,limit,orderStatusArray,sort,order,userId,orderSn);
        return BaseRespVo.ok(map);
    }

    @GetMapping("detail")
    public BaseRespVo orderDetail(@RequestParam("id") Integer id){
        Map<String,Object> map=orderService.queryOrderDetailById(id);
        return BaseRespVo.ok(map);
    }

    @PostMapping("ship")
    public BaseRespVo orderShip(@RequestBody OrderShipVO shipVO){
        orderService.orderShip(shipVO);
        return BaseRespVo.ok();
    }


    @PostMapping("refund")
    public BaseRespVo orderShip(@RequestBody OrderRefundVO OrderRefundVO){
        orderService.orderRefund(OrderRefundVO);
        return BaseRespVo.ok();
    }

    @RequestMapping("reply")
    public BaseRespVo commentReply(){
        return BaseRespVo.err(622,"订单商品已回复");
    }
}