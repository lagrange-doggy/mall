package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Category;

import java.util.List;

public interface WxCatalogService {
    Category getCategoryById(Integer id);

    List<Category> getSubCategoryByPid(Integer id);

    List<Category> getMainCategoryList();
}