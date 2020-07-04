package com.cskaoyan.mall.bean.bo;

import lombok.Data;

@Data
public class GoodsCommentBo {

    private Integer orderGoodsId;

    private String content;

    private Integer userId;

    private Boolean hasPicture;

    private String[] picUrls;

    private Short star;
}