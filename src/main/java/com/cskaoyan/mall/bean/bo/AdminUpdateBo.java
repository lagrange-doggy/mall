package com.cskaoyan.mall.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateBo {

    private int id;
    private String username;
    private String password;
    private String avatar;
    private List<Integer> roleIds;

}
