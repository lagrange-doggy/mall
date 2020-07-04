package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.bean.WxData;
import com.cskaoyan.mall.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/30 9:15
 */
@RestController
@RequestMapping("wx/topic")
public class WxTopicController {

    @Autowired
    TopicService topicService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer size, Integer valueId, Integer type, Integer showType) {
        WxData wxData = topicService.queryTopics(page, size, valueId, type, showType);
        return BaseRespVo.ok(wxData);
    }

    @RequestMapping("detail")
    public BaseRespVo detail(Integer id) {
        Map<String, Object> map = topicService.queryTopic(id);
        return BaseRespVo.ok(map);
    }

    @RequestMapping("related")
    public BaseRespVo related(Integer id) {
        List<Topic> list = topicService.queryRelatedTopics(id);
        return BaseRespVo.ok(list.toArray());
    }
}
