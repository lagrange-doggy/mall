package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ZhidaFeng on 2020/6/27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigOrderVo {
    String cskaoyan_mall_order_unpaid;
    String cskaoyan_mall_order_unconfirm;
    String cskaoyan_mall_order_comment;
}