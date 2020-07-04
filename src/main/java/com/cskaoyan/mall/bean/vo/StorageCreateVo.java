package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageCreateVo {

    private int id;
    private String key;
    private String name;
    private String type;
    private int size;
    private String url;
    private String addTime;
    private String updateTime;

}
