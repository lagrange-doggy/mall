package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: GZ
 * @description: 两级类别展示
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCategoryVO {
    Integer value;
    String label;
    List<BaseLabelAndValueVO> children;
}
