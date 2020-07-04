package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.BrandExample;
import com.cskaoyan.mall.mapper.BrandMapper;
import com.cskaoyan.mall.service.WxBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxBrandServiceImpl implements WxBrandService {
    @Autowired
    BrandMapper brandMapper;

    @Override
    public Brand getBrandById(Integer brandId) {
        return brandMapper.selectByPrimaryKey(brandId);
    }

    @Override
    public List<Brand> getBrandList() {
        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria().andDeletedEqualTo(false);
        return brandMapper.selectByExample(brandExample);
    }

}