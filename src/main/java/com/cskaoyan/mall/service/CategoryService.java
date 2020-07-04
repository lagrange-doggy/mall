package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.vo.CategoryOneVO;

import java.util.List;

public interface CategoryService {
    List<Category> list();

    List<CategoryOneVO> queryLevelOne();

    void addCategory(Category category);

    void deleteCategory(Category category);

    void updateCategory(Category category);
}
