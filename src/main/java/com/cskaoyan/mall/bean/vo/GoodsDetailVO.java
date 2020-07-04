package com.cskaoyan.mall.bean.vo;

import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GoodsAttribute;
import com.cskaoyan.mall.bean.GoodsProduct;
import com.cskaoyan.mall.bean.GoodsSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: GZ
 * @description: 用于商品详情展示的vo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailVO {
    Integer[] categoryIds;
    Goods goods;
    List<GoodsAttribute> attributes;
    List<GoodsSpecification> specifications;
    List<GoodsProduct> products;
}
