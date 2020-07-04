package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ZhidaFeng on 2020/6/27
 * wx homeIndex请求引用了此Vo --by gz
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigWxVo {
    String cskaoyan_mall_wx_index_new;

    String cskaoyan_mall_wx_share;

    String cskaoyan_mall_wx_index_hot;

    String cskaoyan_mall_wx_catlog_goods;

    String cskaoyan_mall_wx_catlog_list;

    String cskaoyan_mall_wx_index_brand;

    String cskaoyan_mall_wx_index_topic;
}
