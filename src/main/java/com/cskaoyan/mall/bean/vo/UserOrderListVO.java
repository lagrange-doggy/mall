package com.cskaoyan.mall.bean.vo;//wx

import com.cskaoyan.mall.bean.HandleOption;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserOrderListVO {

    private BigDecimal actualPrice;

    private List<UserOrderGoodsVO> goodsList;

    private HandleOption handleOption;

    private Integer id;

    private boolean isGroupin;

    private String orderSn;

    @JsonIgnore
    private Short orderStatus;

    private String orderStatusText;
}