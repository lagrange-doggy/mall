package com.cskaoyan.mall.bean.vo;

import com.cskaoyan.mall.bean.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: GZ
 * @description: wx/goods/category
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxGoodsCategoriesVO {
    List<Category> brotherCategory;
    Category currentCategory;
    Category parentCategory;
}
