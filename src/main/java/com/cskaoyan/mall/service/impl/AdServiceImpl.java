package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Ad;
import com.cskaoyan.mall.bean.AdExample;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.mapper.AdMapper;
import com.cskaoyan.mall.mapper.StorageMapper;
import com.cskaoyan.mall.service.AdService;
import com.cskaoyan.mall.service.StorageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/26 14:52
 */
@Service
public class AdServiceImpl implements AdService {

    @Autowired
    AdMapper adMapper;

    @Autowired
    StorageService storageService;

    @Override
    public void createAd(Ad ad) {
        Date date = new Date();
        ad.setAddTime(date);
        ad.setUpdateTime(date);
        ad.setDeleted(false);
        if (ad.getLink() == null) ad.setLink("");
        adMapper.insert(ad);
    }

    @Override
    @Transactional
    public void deleteAd(Ad ad) {
        adMapper.deleteByPrimaryKey(ad.getId());
        storageService.deleteStorageById(null, ad.getUrl());
    }

    @Override
    public void updateAd(Ad ad) {
        ad.setUpdateTime(new Date());
        adMapper.updateByPrimaryKey(ad);
    }

    @Override
    public BaseData queryAds(Integer page, Integer limit, String name, String content, String sort, String order) {
        AdExample adExample = new AdExample();
        AdExample.Criteria criteria = adExample.createCriteria();
        if (name != null) criteria.andNameLike("%"+name+"%");
        if (content != null) criteria.andContentLike("%"+content+"%");
        adExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page,limit);
        List<Ad> ads = adMapper.selectByExample(adExample);
        PageInfo<Ad> pageInfo = new PageInfo<>(ads);
        long total = pageInfo.getTotal();
        return new BaseData(ads, total);
    }
}
