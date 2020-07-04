package com.cskaoyan.mall.bean.vo;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: GZ
 * @description: 所有形如 data{} and count形式的数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxGoodsBaseDataAndCountVO<T>{
    Integer count;
    List<T> data;
}
