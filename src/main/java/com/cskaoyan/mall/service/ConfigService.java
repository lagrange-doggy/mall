package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.vo.ConfigExpressVo;
import com.cskaoyan.mall.bean.vo.ConfigMallVo;
import com.cskaoyan.mall.bean.vo.ConfigOrderVo;
import com.cskaoyan.mall.bean.vo.ConfigWxVo;

import java.util.Map;

/**
 * @ZhidaFeng on 2020/6/27
 **/
public interface ConfigService {
    Map<String, Object> getMallConfig();
    void updateMallConfigPost(ConfigMallVo configMallVo);

    Map<String, Object> getExpressConfig();

    void updateExpressConfigPost(ConfigExpressVo configExpress);

    ConfigOrderVo getOrderConfig();

    void updateOrderConfig(ConfigOrderVo configOrderVo);

    ConfigWxVo getWxConfig();

    void updateWxConfig(ConfigWxVo configWxVo);
}
