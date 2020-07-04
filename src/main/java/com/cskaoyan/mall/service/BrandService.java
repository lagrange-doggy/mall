package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.vo.BrandAddVO;

import java.util.Map;

public interface BrandService {
    Map<String, Object> queryBrandPageList(Integer page, Integer limit, Integer id, String name, String sort, String order);

    boolean delete(Brand brand);

    boolean updateByBrand(Brand brand);

    Brand add(BrandAddVO brandAddVO);
}