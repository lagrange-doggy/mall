package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Comment;
import com.cskaoyan.mall.service.CommentService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: GZ
 * @description: 评论功能
 */
@RestController
@RequestMapping("admin/comment")
public class CommentController {
    @Resource
    CommentService commentService;

    //分页获取评论  & 查询
    @RequestMapping("list")
    public BaseRespVo listComment(Integer page,Integer limit,Integer userId,Integer valueId,String sort,String order){
        BaseData baseData = commentService.queryCommentList(page,limit,userId,valueId,sort,order);
        return BaseRespVo.ok(baseData);
    }

    //评论删除
    @RequestMapping("delete")
    public BaseRespVo deleteComment(@RequestBody Comment comment){
        int i = commentService.deleteComment(comment);
        if(i == 1) return BaseRespVo.ok();
        else return BaseRespVo.err();
    }

}
