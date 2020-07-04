package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Collect;

import java.util.Map;

public interface CollectService {
    BaseData queryCollects(Integer page, Integer limit, Integer userId, Integer valueId, String sort, String order);

    Map<String, Object> queryCollects(Integer page, Integer size, Byte type);

    void updateCollect(Integer type, Integer valueId);

    Collect queruCollectByValueId(Integer valueId);
}
