package com.cskaoyan.mall.bean.vo;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderRefundVO {
    private Integer orderId;
    private BigDecimal refundMoney;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }
}