package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.bo.UpdateGoodsBO;
import com.cskaoyan.mall.bean.vo.GoodsCatAndBrandVO;
import com.cskaoyan.mall.bean.vo.GoodsDetailVO;
import com.cskaoyan.mall.service.GoodsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: GZ
 * @description: 商品
 */
@RestController
@RequestMapping("admin/goods")
public class GoodsController {

    @Resource
    GoodsService goodsService;

    /**
     * @description: 分页获取商品列表, cskaoyanmall_goods
     */
    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, String goodsSn, String name, String sort, String order) {
        BaseData baseData = goodsService.queryGoodsList(page, limit, goodsSn, name, sort, order);
        return BaseRespVo.ok(baseData);
    }

    /**
     * @description: 根据商品id获取商品详情
     * 关联到其他相关表项一并返回
     * @param: [id] 商品id值
     */
    @RequestMapping("detail")
    public BaseRespVo goodsDetail(Integer id) {
        GoodsDetailVO goodsDetailVO = goodsService.queryGoodsDetail(id);
        return BaseRespVo.ok(goodsDetailVO);
    }

    /**
     * @description: 按照K-v格式获取所有商标和品类信息
     */
    @RequestMapping("catAndBrand")
    public BaseRespVo queryCatAndBrandList() {
        GoodsCatAndBrandVO goodsCatAndBrandVO = goodsService.queryCatAndBrandList();
        return BaseRespVo.ok(goodsCatAndBrandVO);
    }

    //删除商品
    @RequestMapping("delete")
    public BaseRespVo deleteGoods(@RequestBody Goods goods){
        int i = goodsService.deleteGoods(goods);
        if(i == 1) return BaseRespVo.ok();
        else return BaseRespVo.err();
    }

    //更新商品
    @RequestMapping("update")
    public BaseRespVo updateGoods(@RequestBody UpdateGoodsBO goodsBO){
        int i = goodsService.updateGoods(goodsBO);
        if(i == 1) return BaseRespVo.ok();
        else return BaseRespVo.err();
    }

    //上架商品
    @RequestMapping("create")
    public BaseRespVo createGoods(@RequestBody UpdateGoodsBO goodsBO){
        int i = goodsService.createGoods(goodsBO);
        if(i == 1) return BaseRespVo.ok();
        else return BaseRespVo.err();
    }
}
