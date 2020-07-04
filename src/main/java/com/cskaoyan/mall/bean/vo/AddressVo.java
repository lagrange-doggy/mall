package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/30 18:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressVo {

    private Boolean isDefault;

    private String detailedAddress;

    private String name;

    private String mobile;

    private Integer id;
}
