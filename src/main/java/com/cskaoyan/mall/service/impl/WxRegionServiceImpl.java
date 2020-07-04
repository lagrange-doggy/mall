package com.cskaoyan.mall.service.impl;


import com.cskaoyan.mall.bean.Region;
import com.cskaoyan.mall.mapper.RegionMapper;
import com.cskaoyan.mall.service.WxRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxRegionServiceImpl implements WxRegionService {
    @Autowired
    RegionMapper regionMapper;


    @Override
    public List<Region> queryRegions(Integer pid) {
        return regionMapper.selectByPid(pid);
    }
}
