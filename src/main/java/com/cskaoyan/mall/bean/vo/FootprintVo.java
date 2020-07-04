package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FootprintVo {

    private String brief;
    private String picUrl;
    private String addTime;
    private int goodsId;
    private String name;
    private int id;
    private int retailPrice;

}
