package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/30 16:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectVo {

    private String brief;

    private String picUrl;

    private Integer valueId;

    private String name;

    private Integer id;

    private Byte type;

    private BigDecimal retailPrice;
}
