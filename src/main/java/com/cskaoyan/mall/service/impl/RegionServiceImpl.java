package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Region;
import com.cskaoyan.mall.bean.RegionExample;
import com.cskaoyan.mall.mapper.RegionMapper;
import com.cskaoyan.mall.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public List<Region> queryRegions() {
        //1
        RegionExample oneRegionExample = new RegionExample();
        oneRegionExample.createCriteria().andTypeEqualTo((byte) 1);
        List<Region> oneRegions = regionMapper.selectByExample(oneRegionExample);
        //2
        RegionExample twoRegionExample = new RegionExample();
        twoRegionExample.createCriteria().andTypeEqualTo((byte) 2);
        List<Region> twoRegions = regionMapper.selectByExample(twoRegionExample);
        //3
        RegionExample threeRegionExample = new RegionExample();
        threeRegionExample.createCriteria().andTypeEqualTo((byte) 3);
        List<Region> threeRegions = regionMapper.selectByExample(threeRegionExample);

        for (int i = 0; i < twoRegions.size(); i++) {
            for (int j = 0; j < threeRegions.size(); j++) {
                Region two = twoRegions.get(i);
                Region three = threeRegions.get(j);
                if (three.getPid().equals(two.getId())) {
                    two.getChildren().add(three);
                }
            }
        }

        for (int i = 0; i < oneRegions.size(); i++) {
            for (int j = 0; j < twoRegions.size(); j++) {
                Region one = oneRegions.get(i);
                Region two = twoRegions.get(j);
                if (two.getPid().equals(one.getId())) {
                    one.getChildren().add(two);
                }
            }
        }
        return oneRegions;
    }
}