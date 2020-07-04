package com.cskaoyan.mall.bean.vo;

import com.cskaoyan.mall.bean.GoodsSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: GZ
 * @description: 对detail的封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxGoodsDetailSpecificationVO {
    String name;
    List<GoodsSpecification> valueList;
}
