package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: GZ
 * @description: 展示K-V形式数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseLabelAndValueVO {
    Integer value;
    String label;
}
