package com.cskaoyan.mall.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 接收从创建管理员页面发送来的数据
 * @author 魏铭志
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreateBo {

    private String username;
    private String password;
    private String avatar;
    private List<Integer> roleIds;
}
