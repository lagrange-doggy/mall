package com.cskaoyan.mall.bean;

import lombok.Data;
import java.util.List;


@Data
public class RegionList {
    Integer id;
    Integer pid;
    String name;
    Integer type;
    Integer code;
    List<Region> children;
}