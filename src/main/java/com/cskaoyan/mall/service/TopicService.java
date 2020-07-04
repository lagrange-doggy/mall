package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.bean.WxData;

import java.util.List;
import java.util.Map;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/26 22:16
 */
public interface TopicService {


    void createTopic(Topic topic);

    void deleteTopic(Topic topic);

    BaseData queryTopics(Integer page, Integer limit, String title, String subtitle, String sort, String order);

    void updateTopic(Topic topic);

    WxData queryTopics(Integer page, Integer size, Integer valueId, Integer type, Integer showType);

    Map<String, Object> queryTopic(Integer id);

    List<Topic> queryRelatedTopics(Integer id);
}
