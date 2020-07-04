package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIndexVo {
    Integer uncomment;
    Integer unpaid;
    Integer unrecv;
    Integer unship;
}
