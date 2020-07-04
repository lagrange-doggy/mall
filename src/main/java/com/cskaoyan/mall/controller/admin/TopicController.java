package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.service.TopicService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/26 22:15
 */
@RestController
@RequestMapping("admin/topic")
public class TopicController {

    @Autowired
    TopicService topicService;

    @RequiresPermissions(value = "admin:topic:list")
    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit,String title, String subtitle, String sort, String order){
        BaseData baseData = topicService.queryTopics(page,limit,title,subtitle,sort,order);
        return BaseRespVo.ok(baseData);
    }

    @RequiresPermissions(value = "admin:topic:create")
    @RequestMapping("create")
    public BaseRespVo create(@RequestBody Topic topic){
        topicService.createTopic(topic);
        return BaseRespVo.ok(topic);
    }

    @RequiresPermissions(value = "admin:topic:delete")
    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Topic topic){
        topicService.deleteTopic(topic);
        return BaseRespVo.ok();
    }

    @RequiresPermissions(value = "admin:topic:update")
    @RequestMapping("update")
    public BaseRespVo update(@RequestBody Topic topic){
        topicService.updateTopic(topic);
        return BaseRespVo.ok();
    }
}
