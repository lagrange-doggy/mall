package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Ad;
import com.cskaoyan.mall.bean.BaseData;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/26 14:50
 */
public interface AdService {

    BaseData queryAds(Integer page, Integer limit, String name, String content, String sort, String order);

    void updateAd(Ad ad);

    void deleteAd(Ad ad);

    void createAd(Ad ad);
}
