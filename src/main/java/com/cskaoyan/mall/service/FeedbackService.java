package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Feedback;

public interface FeedbackService {
    BaseData queryFeedback(Integer page, Integer limit, String sort, String order, Integer id, String username);

    void insertFeedback(Feedback feedback);
}
