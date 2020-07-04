package com.cskaoyan.mall.bean.vo.wxHomeIndexVO;

import com.cskaoyan.mall.bean.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: GZ
 * @description: WxIndex请求 floorGoodsList obj
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxIndexfloorGoodsVO {
    private List<WxIndexGoodsVO> goodsList;
    private Integer id;
    private String name;

    public WxIndexfloorGoodsVO(Category category) {
        this.goodsList = new ArrayList<>();
        this.id = category.getId();
        this.name = category.getName();
    }
}
