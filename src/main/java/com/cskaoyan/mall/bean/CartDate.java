package com.cskaoyan.mall.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ZhidaFeng on 2020/6/30
 * 购物车返回数据
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDate {

    private List<Cart> cartList;

    private CartTotal cartTotal;
}
