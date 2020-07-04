package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Comment;

/**
 * @author: GZ
 * @description:
 */

public interface CommentService {
    //查询comment列表
    BaseData queryCommentList(Integer page, Integer limit, Integer userId, Integer valueId, String sort, String order);
    //删除comment列表
    int deleteComment(Comment comment);
}
