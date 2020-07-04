package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleListVo {

    private int id;
    private String name;
    private boolean enabled;
    private String addTime;
    private String updateTime;
    private boolean deleted;
    private String desc;

}
