package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: GZ
 * @description: wx/comment/list
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxCommentDataVO {
    Date addTime;
    String content;
    String[] picList;
    WxCommentUserInfoVO userInfo;

    public WxCommentDataVO(WxGoodsDetailCommentDataVO commentDataVO) {
        this.addTime = commentDataVO.getAddTime();
        this.content = commentDataVO.getContent();
        this.picList = commentDataVO.getPicList();
        this.userInfo = new WxCommentUserInfoVO(commentDataVO.getNickname(),commentDataVO.getAvatar());
    }
}
