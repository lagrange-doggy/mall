package com.cskaoyan.mall.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    Integer id;
    String name;
    String label;
    Integer parentId;
    String api;
}
