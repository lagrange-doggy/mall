package com.cskaoyan.mall.bean.vo.wxHomeIndexVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: GZ
 * @description: wx index请求的vo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxIndexVO {
    List<WxIndexBannerVO> banner;
    List<WxIndexBrandVO> brandList;
    List<WxIndexChannelVO> channel;
    List<WxIndexCouponVO> couponList;
    List<WxIndexfloorGoodsVO> floorGoodsList;
    List<Object> grouponlist;   //团购先不做
    List<WxIndexGoodsVO> hotGoodsList;
    List<WxIndexGoodsVO> newGoodsList;
    List<WxIndexTopicVO> topicList;
}
