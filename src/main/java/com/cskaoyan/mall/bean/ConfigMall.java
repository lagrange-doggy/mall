package com.cskaoyan.mall.bean;

/**
 * @ZhidaFeng on 2020/6/27
 **/

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigMall {
    private Integer id;

    private String keyName;

    private String keyValue;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date addTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updateTime;

    private Boolean deleted;

    public ConfigMall(String keyName,String keyValue){
        Date date = new Date();
        setAddTime(date);
        setKeyName(keyName);
        setKeyValue(keyValue);
        setDeleted(false);
    }
}