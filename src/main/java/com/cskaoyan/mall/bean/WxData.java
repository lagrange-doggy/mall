package com.cskaoyan.mall.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/30 9:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxData<T> {
    List<T> data;
    long count;
}
