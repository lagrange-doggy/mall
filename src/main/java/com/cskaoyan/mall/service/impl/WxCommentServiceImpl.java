package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.vo.WxCommentDataVO;
import com.cskaoyan.mall.bean.vo.WxCommentVO;
import com.cskaoyan.mall.bean.vo.WxGoodsDetailCommentDataVO;
import com.cskaoyan.mall.mapper.CommentMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.WxCommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: GZ
 * @description: comment
 */
@Service
public class WxCommentServiceImpl implements WxCommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public WxCommentVO queryCommentList(Integer valueId, Integer type, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<WxCommentDataVO> dataList = new ArrayList<>();
        List<WxGoodsDetailCommentDataVO> commentList = commentMapper.selectCommentAndUserByGoodsId(valueId,type,false);
        for (WxGoodsDetailCommentDataVO commentDataVO:commentList){
            dataList.add(new WxCommentDataVO(commentDataVO));
        }
        PageInfo<WxCommentDataVO> PageInfo = new PageInfo<>(dataList);
        int count = (int)PageInfo.getTotal();
        return new WxCommentVO(count,page,dataList);
    }

    @Override
    public Map<String, Integer> commentCount(Integer valueId, Integer type) {
        int countAll = commentMapper.countAllCommentByGoodsId(valueId,type);
        int countHasPic = commentMapper.countHasPic(valueId,type);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("allCount",countAll);
        map.put("hasPicCount",countHasPic);
        return map;
    }
}
