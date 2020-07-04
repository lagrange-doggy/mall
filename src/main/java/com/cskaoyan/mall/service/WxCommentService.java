package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.vo.WxCommentVO;

import java.util.Map;

/**
 * @author: GZ
 * @description: wx commentService
 */
public interface WxCommentService {
    //count all & hasPic
    Map<String, Integer> commentCount(Integer valueId, Integer type);
    // comment list
    WxCommentVO queryCommentList(Integer valueId, Integer type, Integer page, Integer size);
}
