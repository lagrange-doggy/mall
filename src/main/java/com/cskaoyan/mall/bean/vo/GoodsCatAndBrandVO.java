package com.cskaoyan.mall.bean.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: GZ
 * @description: catAndBrand接口所用
 */
@Data
public class GoodsCatAndBrandVO {
    List<GoodsCategoryVO> categoryList;
    List<BaseLabelAndValueVO> brandList;
}
