package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Region;

import java.util.List;

public interface WxRegionService {
    List<Region> queryRegions(Integer pid);
}
