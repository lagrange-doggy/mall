package com.cskaoyan.mall.bean.vo;

import com.cskaoyan.mall.bean.HandleOption;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserOrderInfoVO {
    private Integer id;

    private String orderSn;

    @JsonIgnore
    private Short orderStatus;

    private String consignee;

    private String mobile;

    private String address;

    private BigDecimal goodsPrice;

    private BigDecimal freightPrice;

    private BigDecimal couponPrice;

    @JsonIgnore
    private BigDecimal orderPrice;

    private BigDecimal actualPrice;

    private String expCode;

    private String expNo;

    private Date addTime;

    private HandleOption handleOption;

    private String orderStatusText;

}