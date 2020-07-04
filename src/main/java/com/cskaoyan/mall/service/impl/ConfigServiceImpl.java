package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.vo.ConfigExpressVo;
import com.cskaoyan.mall.bean.vo.ConfigMallVo;
import com.cskaoyan.mall.bean.vo.ConfigOrderVo;
import com.cskaoyan.mall.bean.vo.ConfigWxVo;
import com.cskaoyan.mall.mapper.ConfigMallMapper;
import com.cskaoyan.mall.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ZhidaFeng on 2020/6/27
 **/
@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    ConfigMallMapper configMallMapper;
    @Override
    public Map<String, Object> getMallConfig() {
        ConfigMallExample configMallExample = new ConfigMallExample();
        configMallExample.createCriteria().andKeyNameLike("%mall_mall%").andDeletedEqualTo(false);
        List<ConfigMall> configMalls = configMallMapper.selectByExample(configMallExample);
        Map<String, Object> map = new HashMap<>();
        for (ConfigMall configMall : configMalls) {
            if ("cskaoyan_mall_mall_name".equals(configMall.getKeyName())){
                map.put("cskaoyan_mall_mall_name",configMall.getKeyValue());
            }
            if ("cskaoyan_mall_mall_address".equals(configMall.getKeyName())){
                map.put("cskaoyan_mall_mall_address",configMall.getKeyValue());
            }
            if ("cskaoyan_mall_mall_phone".equals(configMall.getKeyName())){
                map.put("cskaoyan_mall_mall_phone",configMall.getKeyValue());
            }
            if ("cskaoyan_mall_mall_qq".equals(configMall.getKeyName())){
                map.put("cskaoyan_mall_mall_qq",configMall.getKeyValue());
            }
        }
        return map;
    }

    @Override
    public void updateMallConfigPost(ConfigMallVo configMallVo) {
        ConfigMallExample configMallExample = new ConfigMallExample();
        configMallExample.createCriteria().andKeyNameLike("%mall_mall%");
        ConfigMall configMallOlder = new ConfigMall();
        configMallOlder.setDeleted(true);
        configMallMapper.updateByExampleSelective(configMallOlder,configMallExample);

        ConfigMall configMallname = new ConfigMall("cskaoyan_mall_mall_name",configMallVo.getCskaoyan_mall_mall_name());
        configMallMapper.insert(configMallname);
        ConfigMall configMalladdress = new ConfigMall("cskaoyan_mall_mall_address",configMallVo.getCskaoyan_mall_mall_address());
        configMallMapper.insert(configMalladdress);
        ConfigMall configMallphone = new ConfigMall("cskaoyan_mall_mall_phone",configMallVo.getCskaoyan_mall_mall_phone());
        configMallMapper.insert(configMallphone);
        ConfigMall configMallqq = new ConfigMall("cskaoyan_mall_mall_qq", configMallVo.getCskaoyan_mall_mall_qq());
        configMallMapper.insert(configMallqq);


    }

    @Override
    public Map<String, Object> getExpressConfig() {
        ConfigMallExample configMallExample = new ConfigMallExample();
        configMallExample.createCriteria().andKeyNameLike("%mall_express%").andDeletedEqualTo(false);
        List<ConfigMall> configMalls = configMallMapper.selectByExample(configMallExample);
        Map<String, Object> map = new HashMap<>();
        for (ConfigMall configMall : configMalls) {
           if ("cskaoyan_mall_express_freight_min".equals(configMall.getKeyName())){
               map.put("cskaoyan_mall_express_freight_min",configMall.getKeyValue());
           }
            if ("cskaoyan_mall_express_freight_value".equals(configMall.getKeyName())){
                map.put("cskaoyan_mall_express_freight_value",configMall.getKeyValue());
            }
        }
        return map;
    }

    @Override
    public void updateExpressConfigPost(ConfigExpressVo configExpress) {
        ConfigMallExample configMallExample = new ConfigMallExample();
        configMallExample.createCriteria().andKeyNameLike("%mall_express%");
        ConfigMall configMallOdler = new ConfigMall();
        configMallOdler.setDeleted(true);
        configMallMapper.updateByExampleSelective(configMallOdler,configMallExample);

        ConfigMall configMallMin = new ConfigMall("cskaoyan_mall_express_freight_min",configExpress.getCskaoyan_mall_express_freight_min());
        configMallMapper.insert(configMallMin);
        ConfigMall configMallValue = new ConfigMall("cskaoyan_mall_express_freight_value",configExpress.getCskaoyan_mall_express_freight_value());
        configMallMapper.insert(configMallValue);
    }

    @Override
    public ConfigOrderVo getOrderConfig() {
        ConfigMallExample configMallExample = new ConfigMallExample();
        configMallExample.createCriteria().andKeyNameLike("%mall_order%").andDeletedEqualTo(false);
        List<ConfigMall> configMalls = configMallMapper.selectByExample(configMallExample);
        ConfigOrderVo configOrderVo = new ConfigOrderVo();
        for (ConfigMall configMall : configMalls) {
            if ("cskaoyan_mall_order_unpaid".equals(configMall.getKeyName())){
                configOrderVo.setCskaoyan_mall_order_unpaid(configMall.getKeyValue());
            }
            if ("cskaoyan_mall_order_unconfirm".equals(configMall.getKeyName())){
                configOrderVo.setCskaoyan_mall_order_unconfirm(configMall.getKeyValue());
            }
            if ("cskaoyan_mall_order_comment".equals(configMall.getKeyName())){
                configOrderVo.setCskaoyan_mall_order_comment(configMall.getKeyValue());
            }
        }
        return configOrderVo;
    }

    @Override
    public void updateOrderConfig(ConfigOrderVo configOrderVo) {
        ConfigMallExample configMallExample = new ConfigMallExample();
        configMallExample.createCriteria().andKeyNameLike("%mall_order%").andDeletedEqualTo(false);
        ConfigMall configMallOlder = new ConfigMall();
        configMallOlder.setDeleted(true);
        configMallMapper.updateByExampleSelective(configMallOlder,configMallExample);

        ConfigMall configMallunpaid = new ConfigMall("cskaoyan_mall_order_unpaid",configOrderVo.getCskaoyan_mall_order_unpaid());
        configMallMapper.insertSelective(configMallunpaid);
        ConfigMall configMallunconfirm = new ConfigMall("cskaoyan_mall_order_unconfirm",configOrderVo.getCskaoyan_mall_order_unconfirm());
        configMallMapper.insertSelective(configMallunconfirm);
        ConfigMall configMallcomment = new ConfigMall("cskaoyan_mall_order_comment",configOrderVo.getCskaoyan_mall_order_comment());
        configMallMapper.insertSelective(configMallcomment);
    }

    @Override
    public ConfigWxVo getWxConfig() {
        ConfigMallExample configMallExample = new ConfigMallExample();
        configMallExample.createCriteria().andKeyNameLike("%mall_wx%").andDeletedEqualTo(false);
        List<ConfigMall> configMalls = configMallMapper.selectByExample(configMallExample);
        ConfigWxVo configWxVo = new ConfigWxVo();
        for (ConfigMall configMall : configMalls) {
            if ("cskaoyan_mall_wx_index_new".equals(configMall.getKeyName())){
                configWxVo.setCskaoyan_mall_wx_index_new(configMall.getKeyValue());
            }
            if ("cskaoyan_mall_wx_share".equals(configMall.getKeyName())){
                configWxVo.setCskaoyan_mall_wx_share(configMall.getKeyValue());
            }
            if ("cskaoyan_mall_wx_index_hot".equals(configMall.getKeyName())){
                configWxVo.setCskaoyan_mall_wx_index_hot(configMall.getKeyValue());
            }
            if ("cskaoyan_mall_wx_catlog_goods".equals(configMall.getKeyName())){
                configWxVo.setCskaoyan_mall_wx_catlog_goods(configMall.getKeyValue());
            }
            if ("cskaoyan_mall_wx_catlog_list".equals(configMall.getKeyName())){
                configWxVo.setCskaoyan_mall_wx_catlog_list(configMall.getKeyValue());
            }
            if ("cskaoyan_mall_wx_index_brand".equals(configMall.getKeyName())){
                configWxVo.setCskaoyan_mall_wx_index_brand(configMall.getKeyValue());
            }
            if ("cskaoyan_mall_wx_index_topic".equals(configMall.getKeyName())){
                configWxVo.setCskaoyan_mall_wx_index_topic(configMall.getKeyValue());
            }
        }
        return configWxVo;
    }

    @Override
    public void updateWxConfig(ConfigWxVo configWxVo) {
        ConfigMallExample configMallExample = new ConfigMallExample();
        configMallExample.createCriteria().andKeyNameLike("%mall_wx%").andDeletedEqualTo(false);
        ConfigMall configMallOlder = new ConfigMall();
        configMallOlder.setDeleted(true);
        configMallMapper.updateByExampleSelective(configMallOlder,configMallExample);

        ConfigMall configMallNew = new ConfigMall("cskaoyan_mall_wx_index_new",configWxVo.getCskaoyan_mall_wx_index_new());
        configMallMapper.insertSelective(configMallNew);

        ConfigMall configMallShare = new ConfigMall("cskaoyan_mall_wx_share",configWxVo.getCskaoyan_mall_wx_share());
        configMallMapper.insertSelective(configMallShare);

        ConfigMall configMallHot = new ConfigMall("cskaoyan_mall_wx_index_hot",configWxVo.getCskaoyan_mall_wx_index_hot());
        configMallMapper.insertSelective(configMallHot);

        ConfigMall configMallGoods = new ConfigMall("cskaoyan_mall_wx_catlog_goods",configWxVo.getCskaoyan_mall_wx_catlog_goods());
        configMallMapper.insertSelective(configMallGoods);

        ConfigMall configMallList = new ConfigMall("cskaoyan_mall_wx_catlog_list",configWxVo.getCskaoyan_mall_wx_catlog_list());
        configMallMapper.insertSelective(configMallList);

        ConfigMall configMallBrand = new ConfigMall("cskaoyan_mall_wx_index_brand",configWxVo.getCskaoyan_mall_wx_index_brand());
        configMallMapper.insertSelective(configMallBrand);

        ConfigMall configMalltopic = new ConfigMall("cskaoyan_mall_wx_index_topic",configWxVo.getCskaoyan_mall_wx_index_topic());
        configMallMapper.insertSelective(configMalltopic);

    }

}
