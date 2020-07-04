package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.bo.UpdateGoodsBO;
import com.cskaoyan.mall.bean.vo.GoodsCatAndBrandVO;
import com.cskaoyan.mall.bean.vo.GoodsDetailVO;


/**
 * @author: GZ
 * @description: 商品service接口
 */
public interface GoodsService {
    //获取商品列表
    BaseData queryGoodsList(Integer page,Integer limit,String goodsSn,String name,String sort,String order);
    //获取商品详情
    GoodsDetailVO queryGoodsDetail(Integer id);
    //获取商标和品类
    GoodsCatAndBrandVO queryCatAndBrandList();
    //删除商品
    int deleteGoods(Goods goods);
    //更新商品
    int updateGoods(UpdateGoodsBO goodsBO);
    //创建商品
    int createGoods(UpdateGoodsBO goodsBO);
}
