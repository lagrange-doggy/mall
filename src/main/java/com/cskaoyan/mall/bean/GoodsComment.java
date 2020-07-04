package com.cskaoyan.mall.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ZhidaFeng on 2020/7/3
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsComment {
    private Date addTime;
    private String avatar;
    private String content;
    private Integer id;
    private String nickname;
    private String[] picList;
}
