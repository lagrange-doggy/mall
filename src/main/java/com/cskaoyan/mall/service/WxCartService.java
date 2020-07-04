package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.CartDate;

import java.util.List;
import java.util.Map;

public interface WxCartService {
    CartDate getCartDate();

    CartDate updateChecked(List productIds, boolean isChecked);


    int cartAdd(Integer goodsId, Integer number, Integer productId) throws Exception;

    void updateCart(Integer goodsId, Integer id, Integer number, Integer productId) throws Exception;

    int cartGoodsCount() throws Exception;


    int cartFastAdd(Integer goodsId, Integer number, Integer productId) throws Exception;


    CartDate deletedCart(List<Integer> productIds) throws Exception;

    Map cartCheckOut(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId) throws Exception;
}
