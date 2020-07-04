package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateVo {

    private int id;
    private String username;
    private String password;
    private String avatar;
    private String updateTime;
    private List<Integer> roleIds;

}
