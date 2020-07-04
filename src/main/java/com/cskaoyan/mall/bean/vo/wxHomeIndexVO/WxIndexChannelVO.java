package com.cskaoyan.mall.bean.vo.wxHomeIndexVO;

import com.cskaoyan.mall.bean.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: GZ
 * @description: wx index 请求channel obj
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxIndexChannelVO {
    private Integer id;

    private String name;

    private String iconUrl;

    public WxIndexChannelVO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.iconUrl = category.getIconUrl();
    }
}
