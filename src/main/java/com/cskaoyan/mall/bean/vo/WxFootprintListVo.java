package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxFootprintListVo {
    List<FootprintVo> footprintList;
    Integer totalPages;

}
