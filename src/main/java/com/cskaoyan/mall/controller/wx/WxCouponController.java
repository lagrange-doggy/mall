package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.bean.WxData;
import com.cskaoyan.mall.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/30 10:43
 */
@RestController
@RequestMapping("wx/coupon")
public class WxCouponController {

    @Autowired
    CouponService couponService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer size) {
        WxData wxData = couponService.queryCoupons(page, size);
        return BaseRespVo.ok(wxData);
    }

    @RequestMapping("mylist")
    public BaseRespVo mylist(Integer page, Short status, Integer size) {
        WxData wxData = couponService.queryCoupons(status, page, size);
        return BaseRespVo.ok(wxData);
    }

    @RequestMapping("selectlist")
    public BaseRespVo selectlist(Integer cartId, Integer grouponRulesId) {
        List<Coupon> list = couponService.queryCoupon(cartId, grouponRulesId);
        return BaseRespVo.ok(list.toArray());
    }

    @RequestMapping("receive")
    public BaseRespVo receive(@RequestBody Map<String, Integer> map) throws Exception {
        Integer couponId = map.get("couponId");
        int num = couponService.receiveCoupon(couponId);
        if (num == 0) return BaseRespVo.err(740, "优惠券已领完");
        return BaseRespVo.ok();
    }

    @RequestMapping("exchange")
    public BaseRespVo exchange(@RequestBody Map<String, String> map) {
        int num = couponService.exchangeCoupon(map.get("code"));
        if(num == 0) return BaseRespVo.err(742, "兑换码不正确");
        return BaseRespVo.ok();
    }
}
