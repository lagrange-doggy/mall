package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.vo.WxGoodsCategoriesVO;
import com.cskaoyan.mall.bean.vo.WxGoodsDetailVO;
import com.cskaoyan.mall.bean.vo.WxGoodsListByCategoryVO;
import com.cskaoyan.mall.bean.vo.wxHomeIndexVO.WxIndexGoodsVO;

import java.util.List;
import java.util.Map;

/**
 * @author: GZ
 * @description: goods service
 */
public interface WxGoodsService {
    //count sale goods
    Long countSaleGoods();
    //get categories
    WxGoodsCategoriesVO queryGoodsCategoriesById(Integer id);
    // get goodsList
    WxGoodsListByCategoryVO queryGoodsListByCategoryId(Integer categoryId,Integer page, Integer size, String sort, String order, String keyword,Boolean isHot,Boolean isNew,Integer brandId);
    // wx goods detail
    WxGoodsDetailVO queryGoodsDetail(Integer id);
    // wx goods related
    List<WxIndexGoodsVO> querySameCategoryAndNew(Integer id);

    Map<String, Object> wxGoodsDetail(Integer id);
}
