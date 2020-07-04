package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Comment;
import com.cskaoyan.mall.bean.CommentExample;
import com.cskaoyan.mall.mapper.CommentMapper;
import com.cskaoyan.mall.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: GZ
 * @description: 评论相关的接口
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    CommentMapper commentMapper;

    //delete
    @Override
    public int deleteComment(Comment comment) {
        int result = commentMapper.logicalDeleteCommentById(comment.getId());
        return result;
    }

    //list
    @Override
    public BaseData queryCommentList(Integer page, Integer limit, Integer userId, Integer valueId, String sort, String order) {
        CommentExample commentExample = new CommentExample();
        if (userId == null && valueId == null){
            //查询没有被逻辑删除的
            commentExample.createCriteria().andDeletedEqualTo(false);
        }else if (userId == null){
            commentExample.createCriteria().andDeletedEqualTo(false).andValueIdEqualTo(valueId);
        }else if(valueId == null){
            commentExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userId);
        }else commentExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userId).andValueIdEqualTo(valueId);

        commentExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        //pageHelper分页
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
        long total = pageInfo.getTotal();
        return new BaseData(commentList, total);
    }
}
