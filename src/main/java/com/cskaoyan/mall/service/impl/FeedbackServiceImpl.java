package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Feedback;
import com.cskaoyan.mall.bean.FeedbackExample;
import com.cskaoyan.mall.mapper.FeedbackMapper;
import com.cskaoyan.mall.service.FeedbackService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    FeedbackMapper feedbackMapper;

    @Override
    public BaseData queryFeedback(Integer page, Integer limit, String sort, String order, Integer id, String username) {
        FeedbackExample feedbackExample = new FeedbackExample();

        if(id != null && username == null){
            feedbackExample.createCriteria().andIdEqualTo(id);
        }
        if(id == null && username != null && username != ""){
            feedbackExample.createCriteria().andUsernameEqualTo(username);
        }
        if(id != null && username != null && username != ""){
            feedbackExample.createCriteria().andIdEqualTo(id).andUsernameEqualTo(username);
        }
        feedbackExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page,limit);
        List<Feedback> feedbacks = feedbackMapper.selectByExample(feedbackExample);
        PageInfo<Feedback> pageInfo = new PageInfo<>(feedbacks);
        long total = pageInfo.getTotal();

        return new BaseData(feedbacks,total);
    }

    @Override
    public void insertFeedback(Feedback feedback) {
        feedback.setAddTime(new Date(System.currentTimeMillis()));
        feedback.setUpdateTime(new Date(System.currentTimeMillis()));
        feedbackMapper.insertSelective(feedback);
    }
}
