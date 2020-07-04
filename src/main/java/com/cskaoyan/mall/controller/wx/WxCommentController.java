package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.vo.WxCommentVO;
import com.cskaoyan.mall.service.WxCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: GZ
 * @description: comment
 */
@RestController
public class WxCommentController {

    @Autowired
    WxCommentService wxCommentService;

    //wx/comment/count
    @RequestMapping("wx/comment/count")
    public BaseRespVo countComment(Integer valueId, Integer type){
        Map<String,Integer> map = wxCommentService.commentCount(valueId,type);
        return BaseRespVo.ok(map);
    }

    //wx/comment/list
    @RequestMapping("wx/comment/list")
    public BaseRespVo commentList(Integer valueId, Integer type,Integer page,Integer size){
        WxCommentVO wxCommentVO = wxCommentService.queryCommentList(valueId,type,page,size);
        return BaseRespVo.ok(wxCommentVO);
    }
}
