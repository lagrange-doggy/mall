package com.cskaoyan.mall.bean.bo;

import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GoodsAttribute;
import com.cskaoyan.mall.bean.GoodsProduct;
import com.cskaoyan.mall.bean.GoodsSpecification;
import lombok.Data;

import java.util.List;

/**
 * @author: GZ
 * @description: 商品更新所用BO
 */
@Data
public class UpdateGoodsBO {
    Goods goods;
    List<GoodsAttribute> attributes;
    List<GoodsProduct> products;
    List<GoodsSpecification> specifications;
}
