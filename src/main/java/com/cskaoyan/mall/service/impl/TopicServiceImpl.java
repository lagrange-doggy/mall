package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.mapper.TopicMapper;
import com.cskaoyan.mall.service.StorageService;
import com.cskaoyan.mall.service.TopicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/26 22:18
 */
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 查询标题3-1个字相同的专题
     * @param id
     * @return
     */
    @Override
    public List<Topic> queryRelatedTopics(Integer id) {
        ArrayList<Topic> list = new ArrayList<>();
        Topic currentTopic = topicMapper.selectByPrimaryKey(id);
        String title = currentTopic.getTitle();
        for (int i = 0; i < title.length() - 3; i++) {
            for (int j = 3; j > 0; j--) {
                String keywords = title.substring(i, i + j);
                TopicExample topicExample = new TopicExample();
                topicExample.setDistinct(true);
                topicExample.createCriteria().andTitleLike("%" + keywords + "%").
                        andIdNotEqualTo(currentTopic.getId());
                List<Topic> topics = topicMapper.selectByExampleWithBLOBs(topicExample);
                list.addAll(topics);
            }
        }
        ArrayList<Topic> result = new ArrayList<>();
        for (Topic topic : list) {
            boolean flag = true;
            for (Topic topic1 : result) {
                if (topic.getId().equals(topic1.getId())) {
                    flag = false;
                    break;
                }
            }
            if (flag) result.add(topic);
        }
        if (result.size() > 4) return result.subList(0, 4);
        return result;
    }

    @Override
    public Map<String, Object> queryTopic(Integer id) {
        Topic topic = topicMapper.selectByPrimaryKey(id);
        String[] goods = topic.getGoods();
        HashMap<String, Object> map = new HashMap<>();
        map.put("topic", topic);
        ArrayList<Goods> list = new ArrayList<>();
        for (String good : goods) {
            int goodsId = Integer.parseInt(good);
            Goods goods1 = goodsMapper.selectByPrimaryKey(goodsId);
            list.add(goods1);
        }
        map.put("goods", list.toArray());
        return map;
    }

    @Override
    public WxData queryTopics(Integer page, Integer size, Integer valueId, Integer type, Integer showType) {
        TopicExample topicExample = new TopicExample();
        PageHelper.startPage(page,size);
        List<Topic> topics = topicMapper.selectByExampleWithBLOBs(topicExample);
        PageInfo<Topic> pageInfo = new PageInfo<>(topics);
        long count = pageInfo.getTotal();
        return new WxData(topics, count);
    }

    @Override
    public void updateTopic(Topic topic) {
        topic.setUpdateTime(new Date());
        topicMapper.updateByPrimaryKeyWithBLOBs(topic);
    }

    @Autowired
    StorageService storageService;
    @Override
    public void deleteTopic(Topic topic) {
        topicMapper.deleteByPrimaryKey(topic.getId());
        storageService.deleteStorageById(null, topic.getPicUrl());
    }

    @Override
    public void createTopic(Topic topic) {
        topic.setAddTime(new Date());
        topic.setDeleted(false);
        topic.setSortOrder(0);
        topicMapper.insert(topic);
    }

    @Override
    public BaseData queryTopics(Integer page, Integer limit, String title, String subtitle, String sort, String order) {
        TopicExample topicExample = new TopicExample();
        TopicExample.Criteria criteria = topicExample.createCriteria();
        if (title != null) criteria.andTitleLike("%" + title +"%");
        if (subtitle != null) criteria.andSubtitleLike("%" + subtitle +"%");
        topicExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page,limit);
        List<Topic> topics = topicMapper.selectByExampleWithBLOBs(topicExample);
        PageInfo<Topic> pageInfo = new PageInfo<>(topics);
        long total = pageInfo.getTotal();
        return new BaseData(topics, total);
    }
}
