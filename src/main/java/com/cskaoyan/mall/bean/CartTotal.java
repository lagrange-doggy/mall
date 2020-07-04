package com.cskaoyan.mall.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ZhidaFeng on 2020/6/30
 **/

@Data
public class CartTotal {

    Integer goodsCount;
    Integer checkedGoodsCount;
    BigDecimal goodsAmount;
    BigDecimal checkedGoodsAmount;
}
