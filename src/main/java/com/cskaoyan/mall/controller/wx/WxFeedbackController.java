package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Feedback;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.service.FeedbackService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wx/feedback")
public class WxFeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @RequestMapping("submit")
    public BaseRespVo submitFeedback(@RequestBody Feedback feedback) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        feedback.setUserId(user.getId());
        feedback.setUsername(user.getUsername());
        feedbackService.insertFeedback(feedback);

        return BaseRespVo.ok();
    }
}
