package com.cskaoyan.mall.bean.vo.wxHomeIndexVO;

import com.cskaoyan.mall.bean.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: GZ
 * @description: wx index请求CouponList obj
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxIndexCouponVO {
    private Integer id;

    private String name;

    private String desc;

    private String tag;

    private BigDecimal discount;

    private Short days;

    private BigDecimal min;

    public WxIndexCouponVO(Coupon coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.desc = coupon.getDesc();
        this.tag = coupon.getTag();
        this.discount = coupon.getDiscount();
        this.days = coupon.getDays();
        this.min = coupon.getMin();
    }
}
