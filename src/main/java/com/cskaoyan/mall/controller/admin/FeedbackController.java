package com.cskaoyan.mall.controller.admin;


import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/feedback")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;
    @RequestMapping("list")
    public BaseRespVo list(Integer page,Integer limit,String sort,String order, Integer id, String username){
        BaseData baseData = feedbackService.queryFeedback(page, limit, sort, order, id, username);
        return BaseRespVo.ok(baseData);
    }
}
