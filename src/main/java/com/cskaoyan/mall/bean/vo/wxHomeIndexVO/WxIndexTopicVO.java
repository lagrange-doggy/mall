package com.cskaoyan.mall.bean.vo.wxHomeIndexVO;

import com.cskaoyan.mall.bean.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: GZ
 * @description: wx index请求中topicList元素
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxIndexTopicVO {
    private Integer id;
    private String picUrl;
    private BigDecimal price;
    private String readCount;
    private String title;
    private String subtitle;

    public WxIndexTopicVO(Topic topic) {
        this.id = topic.getId();
        this.picUrl = topic.getPicUrl();
        this.price = topic.getPrice();
        this.readCount = topic.getReadCount();
        this.title = topic.getTitle();
        this.subtitle = topic.getSubtitle();
    }
}
