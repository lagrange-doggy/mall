package com.cskaoyan.mall.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.BinaryOperator;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/30 19:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressCreateBo {

    private Integer id;

    private Boolean isDefault;

    private Integer provinceId;

    private Integer cityId;

    private Integer areaId;

    private String name;

    private String address;

    private String mobile;
}
