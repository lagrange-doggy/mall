package com.cskaoyan.mall.config;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyException extends RuntimeException{

    private Integer errno;
    private String errmsg;

}