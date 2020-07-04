package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.vo.WxGoodsCategoriesVO;
import com.cskaoyan.mall.bean.vo.WxGoodsDetailVO;
import com.cskaoyan.mall.bean.vo.WxGoodsListByCategoryVO;
import com.cskaoyan.mall.bean.vo.wxHomeIndexVO.WxIndexGoodsVO;
import com.cskaoyan.mall.service.FootprintService;
import com.cskaoyan.mall.service.HistoryService;
import com.cskaoyan.mall.service.WxGoodsService;
import com.cskaoyan.mall.utils.UserUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: GZ
 * @description: 商品相关的处理逻辑
 */
@RestController
public class WxGoodsController {
    @Autowired
    FootprintService footprintService;
    @Autowired
    WxGoodsService wxGoodsService;
    @Autowired
    HistoryService historyService;

    //首页count在售商品
    @RequestMapping("wx/goods/count")
    public BaseRespVo countGoodsIsSale(){
        Map<String,Long> map = new HashMap<>();
        Long count = wxGoodsService.countSaleGoods();
        map.put("goodsCount",count);
        return BaseRespVo.ok(map);
    }

    //goods categories
    @RequestMapping("wx/goods/category")
    public BaseRespVo queryGoodsCategories(Integer id){
        WxGoodsCategoriesVO wxGoodsCategoriesVO = wxGoodsService.queryGoodsCategoriesById(id);
        return BaseRespVo.ok(wxGoodsCategoriesVO);
    }

    // goods list
    @RequestMapping("wx/goods/list")
    public BaseRespVo listByCategoryId(Integer categoryId,Integer page, Integer size, String sort, String order, String keyword,Boolean isHot,Boolean isNew,Integer brandId){
        WxGoodsListByCategoryVO wxGoodsListByCategoryVO = wxGoodsService.queryGoodsListByCategoryId(categoryId,page,size,sort,order,keyword,isHot,isNew,brandId);
        if (SecurityUtils.getSubject().getPrincipal() != null && !(keyword==null || "".equals(keyword))){
            historyService.insertHistory(UserUtil.getUserId(),keyword);
        }
        return BaseRespVo.ok(wxGoodsListByCategoryVO);
    }

    /*//goods detail
    @RequestMapping("wx/goods/detail")
    public BaseRespVo goodsDetail(Integer id){
        Map<String,Object> map = wxGoodsService.wxGoodsDetail(id);
        if (SecurityUtils.getSubject().getPrincipal() != null){
            footprintService.insertFootprint(UserUtil.getUserId(),id);
        }
        return BaseRespVo.ok(map);
    }*/

    //goods detail
    @RequestMapping("wx/goods/detail")
    public BaseRespVo goodsDetail(Integer id){
        WxGoodsDetailVO  wxGoodsDetailVO = wxGoodsService.queryGoodsDetail(id);
        if (SecurityUtils.getSubject().getPrincipal() != null){
            footprintService.insertFootprint(UserUtil.getUserId(),id);
        }
        return BaseRespVo.ok(wxGoodsDetailVO);
    }


    //goods related
    @RequestMapping("wx/goods/related")
    public BaseRespVo goodsRelated(Integer id){
        List<WxIndexGoodsVO> goodsVOList = wxGoodsService.querySameCategoryAndNew(id);
        Map<String,List> map = new HashMap<>();
        map.put("goodsList",goodsVOList);
        return BaseRespVo.ok(map);
    }
}
