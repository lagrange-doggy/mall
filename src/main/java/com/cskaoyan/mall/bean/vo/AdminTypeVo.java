package com.cskaoyan.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 魏铭志
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminTypeVo {

//    //超级管理员
//    SuperAdmin(1, "超级管理员"),
//    //商城管理员
//    MarkAdmin(2, "商场管理员"),
//    //推广管理员
//    AdAdmin(3,"推广管理员");


    //代号
    private int value;
    //字段
    private String label;


}
