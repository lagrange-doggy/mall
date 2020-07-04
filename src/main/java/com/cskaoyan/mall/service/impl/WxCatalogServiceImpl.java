package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.CategoryExample;
import com.cskaoyan.mall.mapper.CategoryMapper;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.service.WxCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxCatalogServiceImpl implements WxCatalogService {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public Category getCategoryById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Category> getSubCategoryByPid(Integer id) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andPidEqualTo(id);
        return categoryMapper.selectByExample(categoryExample);
    }

    @Override
    public List<Category> getMainCategoryList() {

        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andPidEqualTo(0);
        return  categoryMapper.selectByExample(categoryExample);
    }
}