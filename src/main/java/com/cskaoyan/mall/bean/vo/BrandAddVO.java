package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandAddVO {
    private String name;
    private String desc;
    private BigDecimal floorPrice;
    private String picUrl;
}
