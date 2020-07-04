package com.cskaoyan.mall.bean.vo;

import com.cskaoyan.mall.bean.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: GZ
 * @description: wx/goods/detail
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxGoodsDetailVO {
    List<GoodsAttribute> attribute;
    Brand brand;
    WxGoodsBaseDataAndCountVO<WxGoodsBaseDataAndCountVO> comment;
    List groupon;   //团购暂时不写
    Goods info;
    List<Issue> issue;
    List<GoodsProduct> productList;
    String shareImage;
    List<WxGoodsDetailSpecificationVO> specificationList;
    Integer userHasCollect;

}
