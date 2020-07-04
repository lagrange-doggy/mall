package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.bean.CouponUser;
import com.cskaoyan.mall.bean.WxData;

import java.util.List;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/26 20:52
 */
public interface CouponService {

    BaseData queryCoupons(Integer page, String name, Short type, Short status, Integer limit, String sort, String order);

    void createCoupon(Coupon coupon);

    void deleteCoupon(Coupon coupon);

    void updateCoupon(Coupon coupon);

    Coupon queryCouponById(Integer id);

    BaseData queryCoupon(Integer page, Integer limit, Integer couponId, Integer userId, Short status, String sort, String order);

    WxData queryCoupons(Integer page, Integer size);

    WxData queryCoupons(Short status, Integer page, Integer size);

    List<Coupon> queryCoupon(Integer cartId, Integer grouponRulesId);

    int receiveCoupon(Integer couponId) throws Exception;

    int exchangeCoupon(String code);

    void createCouponUser(CouponUser couponUser);
}
