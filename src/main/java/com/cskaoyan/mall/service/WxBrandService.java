package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Brand;

import java.util.List;

public interface WxBrandService {
    Brand getBrandById(Integer brandId);

    List<Brand> getBrandList();
}