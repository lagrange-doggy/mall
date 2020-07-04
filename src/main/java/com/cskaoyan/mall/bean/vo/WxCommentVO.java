package com.cskaoyan.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: GZ
 * @description: wx/comment/list
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxCommentVO {
    Integer count;
    Integer currentPage;
    List<WxCommentDataVO> data;
}
