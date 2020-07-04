package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.vo.ConfigWxVo;
import com.cskaoyan.mall.bean.vo.wxHomeIndexVO.WxIndexVO;

/**
 * @author: GZ
 * @description: wx home service
 */
public interface WxHomeService {
    //根据配置文件，查询所有要在首页展示的信息
    WxIndexVO queryIndex(ConfigWxVo wxConfig);
}
