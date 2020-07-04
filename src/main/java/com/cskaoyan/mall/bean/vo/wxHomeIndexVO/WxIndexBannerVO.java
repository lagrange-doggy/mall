package com.cskaoyan.mall.bean.vo.wxHomeIndexVO;

import com.cskaoyan.mall.bean.Ad;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: GZ
 * @description: wx index请求 banner obj 对应bean 中Ad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxIndexBannerVO {
    private Integer id;

    private String name;

    private String link;

    private String url;

    private Byte position;

    private String content;

    private Boolean enabled;

    private Date addTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss",timezone = "GMT+8")
    private Date updateTime;

    private Boolean deleted;

    public WxIndexBannerVO(Ad ad) {
        this.id = ad.getId();
        this.name = ad.getName();
        this.link = ad.getLink();
        this.url = ad.getUrl();
        this.position = ad.getPosition();
        this.content = ad.getContent();
        this.enabled = ad.getEnabled();
        this.addTime = ad.getAddTime();
        this.updateTime = ad.getUpdateTime();
        this.deleted = ad.getDeleted();
    }
}
