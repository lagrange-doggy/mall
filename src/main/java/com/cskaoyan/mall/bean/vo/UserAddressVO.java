package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @description: 用户地址管理进行封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressVO {

    private Integer id;

    private String name;

    private Integer userId;

    private Integer provinceId;

    private Integer cityId;

    private Integer areaId;

    private String address;

    private String mobile;

    private Boolean isDefault;

    private String province;

    private String city;

    private String area;

}
