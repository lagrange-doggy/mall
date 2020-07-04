package com.cskaoyan.mall.bean.vo.wxHomeIndexVO;

import com.cskaoyan.mall.bean.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: GZ
 * @description: wx index请求 brand obj
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxIndexBrandVO {
    private Integer id;

    private String name;

    private String desc;

    private String picUrl;

    private BigDecimal floorPrice;

    public WxIndexBrandVO(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.desc = brand.getDesc();
        this.picUrl = brand.getPicUrl();
        this.floorPrice = brand.getFloorPrice();
    }
}
