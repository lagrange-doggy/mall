package com.cskaoyan.mall.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ZhidaFeng on 2020/6/29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard {
    private long goodsTotal;
    private long orderTotal;
    private long productTotal;
    private long userTotal;
}
