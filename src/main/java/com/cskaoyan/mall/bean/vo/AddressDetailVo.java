package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/30 19:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetailVo {

    private Boolean isDefault;

    private Integer areaId;

    private Integer cityId;

    private Integer provinceId;

    private String address;

    private String cityName;

    private String areaName;

    private String provinceName;

    private String name;

    private String mobile;

    private Integer id;
}