package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.Date;

/**
 * @ZhidaFeng on 2020/7/2
 **/
@Data
public class GoodsChecked {
    private Integer id;

    private Integer checked;

    private Integer userId;

    private Integer goodsId;

    private String goodsSn;

    private String goodsName;

    private String productId;

    private Double price;

    private Integer number;

    private String specifications;

    private String picUrl;

    private Date addTime;

    private Date updateTime;

    private boolean deleted;
}
