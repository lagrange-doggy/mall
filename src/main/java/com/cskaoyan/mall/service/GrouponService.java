package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.GrouponRules;

import java.util.Map;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/26 22:27
 */
public interface GrouponService {

    BaseData queryGrouponRules(Integer page, Integer limit, Integer goodsId, String sort, String order);

    void createGrouponRule(GrouponRules grouponRules);

    void deleteGrouponRule(GrouponRules grouponRules);

    BaseData queryGroupons(Integer page, Integer limit, Integer goodsId, String sort, String order);

    void updateGrouponRule(GrouponRules grouponRules);

}
