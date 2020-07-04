package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.service.CouponService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/26 20:44
 */
@RestController
@RequestMapping("admin/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @RequiresPermissions(value = "admin:coupon:list")
    @RequestMapping("list")
    public BaseRespVo list(Integer page,String name, Short type, Short status, Integer limit,String sort, String order){
        BaseData baseData = couponService.queryCoupons(page,name, type, status, limit,sort,order);
        return BaseRespVo.ok(baseData);
    }

    @RequiresPermissions(value = "admin:coupon:create")
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody Coupon coupon) {
        couponService.createCoupon(coupon);
        return BaseRespVo.ok();
    }

    @RequiresPermissions(value = "admin:coupon:delete")
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Coupon coupon) {
        couponService.deleteCoupon(coupon);
        return BaseRespVo.ok();
    }

    @RequiresPermissions(value = "admin:coupon:update")
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody Coupon coupon) {
        couponService.updateCoupon(coupon);
        return BaseRespVo.ok(coupon);
    }

    @RequiresPermissions(value = "admin:coupon:read")
    @RequestMapping("read")
    public BaseRespVo read(Integer id) {
        Coupon coupon = couponService.queryCouponById(id);
        return BaseRespVo.ok(coupon);
    }

    @RequestMapping("listuser")
    public BaseRespVo listuser(Integer page, Integer limit, Integer couponId, Integer userId,  Short status, String sort, String order) {
        BaseData coupon = couponService.queryCoupon(page, limit, couponId, userId, status, sort, order);
        return BaseRespVo.ok(coupon);
    }
}
