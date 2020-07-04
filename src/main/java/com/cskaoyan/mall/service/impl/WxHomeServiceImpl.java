package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.vo.ConfigWxVo;
import com.cskaoyan.mall.bean.vo.wxHomeIndexVO.*;
import com.cskaoyan.mall.mapper.*;
import com.cskaoyan.mall.service.CouponService;
import com.cskaoyan.mall.service.WxHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: GZ
 * @description: home serviceImpl
 */
@Service
public class WxHomeServiceImpl implements WxHomeService {
    @Autowired
    AdMapper adMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    CouponService couponService;

    //查询所有要在首页展示的信息
    @Override
    public WxIndexVO queryIndex(ConfigWxVo wxConfig) {
        //单层 & 多层
        AdExample adExample = new AdExample();
        adExample.createCriteria().andDeletedEqualTo(false);
        List<Ad> ads = adMapper.selectByExample(adExample);
        List<WxIndexBannerVO> banner = new ArrayList<>();
        for (Ad ad : ads) {
            banner.add(new WxIndexBannerVO(ad));
        }

        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria().andDeletedEqualTo(false);
        brandExample.setOrderByClause(" id limit " + wxConfig.getCskaoyan_mall_wx_index_brand());
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        List<WxIndexBrandVO> brandList = new ArrayList<>();
        for (Brand b : brands) {
            brandList.add(new WxIndexBrandVO(b));
        }

        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andPidEqualTo(0).andDeletedEqualTo(false);
        List<Category> categoriesFirst = categoryMapper.selectByExample(categoryExample);
        List<WxIndexChannelVO> channel = new ArrayList<>();
        for (Category c : categoriesFirst) {
            channel.add(new WxIndexChannelVO(c));
        }

        /*// 换成xjn接口    com.cskaoyan.mall.service.CouponService.queryCoupons(java.lang.Integer, java.lang.Integer)
        CouponExample couponExample = new CouponExample();
        couponExample.createCriteria().andDeletedEqualTo(false);
        couponExample.setOrderByClause("id limit 3");
        List<Coupon> coupons = couponMapper.selectByExample(couponExample);*/
        WxData wxData = couponService.queryCoupons(0, 3);
        List<WxIndexCouponVO> couponList = new ArrayList<>();
        List<Coupon> coupons = wxData.getData().subList(0,3);   //取coupons要展示的前三张
        for (Coupon c : coupons) {
            couponList.add(new WxIndexCouponVO(c));
        }

        CategoryExample floorGoodsCategory = new CategoryExample();
        floorGoodsCategory.createCriteria().andDeletedEqualTo(false).andPidEqualTo(0);
        floorGoodsCategory.setOrderByClause("id limit " + wxConfig.getCskaoyan_mall_wx_catlog_list());
        List<Category> floorGoodsCategories = categoryMapper.selectByExample(floorGoodsCategory);
        List<WxIndexfloorGoodsVO> floorGoodsList = new ArrayList<>();
        for (Category c:floorGoodsCategories){
            WxIndexfloorGoodsVO temp =new WxIndexfloorGoodsVO(c);
            floorGoodsList.add(temp);
            //联表查goodsList
            List<Goods> goodsList = goodsMapper.queryGoodsInCategories(c.getId(), Integer.parseInt(wxConfig.getCskaoyan_mall_wx_catlog_goods()));
            for (Goods g:goodsList){
                temp.getGoodsList().add(new WxIndexGoodsVO(g));
            }
        }

        GoodsExample goodsExample1 = new GoodsExample();
        goodsExample1.createCriteria().andDeletedEqualTo(false).andIsHotEqualTo(true);
        goodsExample1.setOrderByClause("id limit " + wxConfig.getCskaoyan_mall_wx_index_hot());
        List<Goods> goodsHot = goodsMapper.selectByExample(goodsExample1);
        List<WxIndexGoodsVO> hotGoodsList = new ArrayList<>();
        for (Goods g : goodsHot) {
            hotGoodsList.add(new WxIndexGoodsVO(g));
        }

        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andDeletedEqualTo(false).andIsNewEqualTo(true);
        goodsExample.setOrderByClause("id limit " + wxConfig.getCskaoyan_mall_wx_index_new());
        List<Goods> goodsNew = goodsMapper.selectByExample(goodsExample);
        List<WxIndexGoodsVO> newGoodsList = new ArrayList<>();
        for (Goods g : goodsNew) {
            newGoodsList.add(new WxIndexGoodsVO(g));
        }

        TopicExample topicExample = new TopicExample();
        topicExample.createCriteria().andDeletedEqualTo(false);
        topicExample.setOrderByClause("id limit " + wxConfig.getCskaoyan_mall_wx_index_topic());
        List<Topic> topics = topicMapper.selectByExampleWithBLOBs(topicExample);
        List<WxIndexTopicVO> topicList = new ArrayList<>();
        for (Topic t : topics) {
            topicList.add(new WxIndexTopicVO(t));
        }
        WxIndexVO wxIndexVO = new WxIndexVO(banner, brandList, channel, couponList, floorGoodsList, null, hotGoodsList, newGoodsList, topicList);
        return wxIndexVO;
    }
}