package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: GZ
 * @description: 商品详情中的comment元素中的data
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxGoodsDetailCommentDataVO {
    Date addTime;
    String avatar;
    String content;
    Integer id;
    String nickname;
    String[] picList;
}
