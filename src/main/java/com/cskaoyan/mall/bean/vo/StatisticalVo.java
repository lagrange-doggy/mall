package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ZhidaFeng on 2020/6/27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticalVo<T> {
    List columns;
    List<T> rows;
}
