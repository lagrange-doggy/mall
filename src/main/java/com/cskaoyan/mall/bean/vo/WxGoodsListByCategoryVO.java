package com.cskaoyan.mall.bean.vo;

import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.vo.wxHomeIndexVO.WxIndexGoodsVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: GZ
 * @description: wx/goods/list
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxGoodsListByCategoryVO {
    Integer count;
    List<Category> filterCategoryList;
    List<WxIndexGoodsVO> goodsList;
}
