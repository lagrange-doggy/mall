package com.cskaoyan.mall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ZhidaFeng on 2020/6/27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatistics {

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    Date day;
    int users;
}
