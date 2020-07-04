package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.bean.vo.UserIndexVo;
import com.cskaoyan.mall.bean.vo.UserOrderVo;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wx/user")
public class WxUserController {
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @GetMapping("index")
    public BaseRespVo userIndex() {
//        MallToken mallToken = (MallToken) SecurityUtils.getSubject().getPrincipal();
//        String username = mallToken.getUsername();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        User user = userService.selectUserByUserName(username);
        user = userService.selectUserByUserName(user.getUsername());
        UserIndexVo order = new UserIndexVo();
        //代付款
        order.setUnpaid(orderService.queryOrderNumByUserIdAndOrderStatus(user.getId(),101));
        //代发货
        order.setUnship(orderService.queryOrderNumByUserIdAndOrderStatus(user.getId(),201));
        //代收货
        order.setUnrecv(orderService.queryOrderNumByUserIdAndOrderStatus(user.getId(),301));
        //代评价
        order.setUncomment(orderService.queryOrderNumByUserIdAndOrderStatus(user.getId(),401) + orderService.queryOrderNumByUserIdAndOrderStatus(user.getId(), 402));
        return BaseRespVo.ok(new UserOrderVo(order));
    }
}
