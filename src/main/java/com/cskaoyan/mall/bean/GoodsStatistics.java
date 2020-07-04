package com.cskaoyan.mall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ZhidaFeng on 2020/6/28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsStatistics {

    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date day;

    private int orders;

    private int products;
}
