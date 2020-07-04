package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.bo.UpdateGoodsBO;
import com.cskaoyan.mall.bean.vo.BaseLabelAndValueVO;
import com.cskaoyan.mall.bean.vo.GoodsCatAndBrandVO;
import com.cskaoyan.mall.bean.vo.GoodsCategoryVO;
import com.cskaoyan.mall.bean.vo.GoodsDetailVO;
import com.cskaoyan.mall.mapper.*;
import com.cskaoyan.mall.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: GZ
 * @description: 商品实现类
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    GoodsMapper goodsMapper;
    @Resource
    BrandMapper brandMapper;
    @Resource
    CategoryMapper categoryMapper;
    @Resource
    GoodsProductMapper goodsProductMapper;
    @Resource
    GoodsAttributeMapper goodsAttributeMapper;
    @Resource
    GoodsSpecificationMapper goodsSpecificationMapper;

    //新建商品
    @Override
    public int createGoods(UpdateGoodsBO goodsBO) {
        //查重,报错
        goodsBO.getGoods().setId(Integer.parseInt(goodsBO.getGoods().getGoodsSn()));
        Goods goods = goodsMapper.selectByPrimaryKey(goodsBO.getGoods().getId());
        if (goods != null) return 0;
        //不重复就insert
        goodsBO.getGoods().setAddTime(new Date());
        goodsBO.getGoods().setUpdateTime(new Date());
        goodsBO.getGoods().setDeleted(false);
        int result = goodsMapper.insert(goodsBO.getGoods());
        for (GoodsAttribute goodsAttribute:goodsBO.getAttributes()){
            //complete & insert
            completeGoodsAttribute(goodsBO, goodsAttribute);
            goodsAttributeMapper.insert(goodsAttribute);
        }
        for (GoodsProduct goodsProduct:goodsBO.getProducts()){
            completeGoodsProductForInsert(goodsBO, goodsProduct);
            goodsProductMapper.insert(goodsProduct);
        }
        for (GoodsSpecification goodsSpecification:goodsBO.getSpecifications()){
            //complete & insert
            completeGoodsSpecification(goodsBO, goodsSpecification);
            goodsSpecificationMapper.insert(goodsSpecification);
        }
        return result;
    }

    private void completeGoodsProductForInsert(UpdateGoodsBO goodsBO, GoodsProduct goodsProduct) {
        goodsProduct.setId(null);   //没有id,gou前端默认传0，影响数据库操作，改成null
        goodsProduct.setGoodsId(goodsBO.getGoods().getId());
        goodsProduct.setAddTime(new Date());    //新增，直接维护
        goodsProduct.setUpdateTime(new Date());
        goodsProduct.setDeleted(false);
    }

    //更新商品
    @Override
    public int updateGoods(UpdateGoodsBO goodsBO) {
        int result = goodsMapper.updateByPrimaryKey(goodsBO.getGoods());
        List<Integer> tempIdsList = new ArrayList<>();  //用来捕捉现有的关联表数据list

        for (GoodsAttribute goodsAttribute : goodsBO.getAttributes()) {
            if (goodsAttribute.getId() == null){
                //新增的要insert,补全再insert，gou前端只传了两项
                completeGoodsAttribute(goodsBO, goodsAttribute);
                goodsAttributeMapper.insert(goodsAttribute);
                tempIdsList.add(goodsAttribute.getId());
            }else{
                //现有的先存list，并更新
                tempIdsList.add(goodsAttribute.getId());
                //goodsAttributeMapper.updateByPrimaryKey(goodsAttribute);  //前端根本就不能编辑，用不着这句
            }
        }   //经过遍历，拿到所有现有id,完成 insert & update,最后 delete
            goodsAttributeMapper.logicalDeleteAttributesForGoodsUpdate(goodsBO.getGoods().getId(),tempIdsList);
            tempIdsList.clear();    //下次备用

        for (GoodsProduct goodsProduct:goodsBO.getProducts()){
            //两种情况： spec变化引起的新增 & spec不变直接set非null
            if (goodsProduct.getId()!=0) {
                goodsProductMapper.updateByPrimaryKeySelective(goodsProduct);
            } else {    //新增
                completeGoodsProductForInsert(goodsBO, goodsProduct);
                goodsProductMapper.insert(goodsProduct);
            }
        }

        for (GoodsSpecification goodsSpecification:goodsBO.getSpecifications()){
            if (goodsSpecification.getId()==null){
                //complete & insert,新的
                completeGoodsSpecification(goodsBO, goodsSpecification);
                goodsSpecificationMapper.insert(goodsSpecification);
                tempIdsList.add(goodsSpecification.getId());
            }else{
                //旧的
                tempIdsList.add(goodsSpecification.getId());
                //goodsSpecificationMapper.updateByPrimaryKey(goodsSpecification);  //前端根本就不能编辑，用不着这句
            }
        }   //delete not in
            goodsSpecificationMapper.logicalDeleteSpecificationForGoodsUpdate(goodsBO.getGoods().getId(),tempIdsList);

        return result;
    }

    private void completeGoodsSpecification(UpdateGoodsBO goodsBO, GoodsSpecification goodsSpecification) {
        goodsSpecification.setAddTime(new Date());
        //attribute & Specification在前端更新就是新建和删除，所以没有实际上的update,addTime直接生成即可
        goodsSpecification.setUpdateTime(new Date());
        goodsSpecification.setDeleted(false);
        goodsSpecification.setGoodsId(goodsBO.getGoods().getId());
    }

    private void completeGoodsAttribute(UpdateGoodsBO goodsBO, GoodsAttribute goodsAttribute) {
        goodsAttribute.setAddTime(new Date());
        goodsAttribute.setUpdateTime(new Date());
        goodsAttribute.setGoodsId(goodsBO.getGoods().getId());
        goodsAttribute.setDeleted(false);
    }

    //删除商品
    @Override
    public int deleteGoods(Goods goods) {
        //按照id进行删除
        Goods tempGoods = goodsMapper.selectByPrimaryKey(goods.getId());
        tempGoods.setDeleted(true);
        int result = goodsMapper.updateByPrimaryKey(tempGoods);
        //删除其他相关表
        int r1 = goodsAttributeMapper.updateDeletedByGoodsId(goods.getId());
        int r2 = goodsProductMapper.updateDeletedByGoodsId(goods.getId());
        int r3 = goodsSpecificationMapper.updateDeletedByGoodsId(goods.getId());
        return result;
    }

    //获取所有品类以及商标
    @Override
    public GoodsCatAndBrandVO queryCatAndBrandList() {
        GoodsCatAndBrandVO goodsCatAndBrandVO = new GoodsCatAndBrandVO();
        List<BaseLabelAndValueVO> brandList = new ArrayList<>();
        //查商标
        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria().andDeletedEqualTo(false);
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        //查类别
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andDeletedEqualTo(false);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        //装brand
        for (Brand b : brands) {
            brandList.add(new BaseLabelAndValueVO(b.getId(), b.getName()));
        }
        goodsCatAndBrandVO.setBrandList(brandList);
        //装category
        List<GoodsCategoryVO> tempCategoryList = new ArrayList<>();
        for (Category cf : categories) {
            if (cf.getPid() == 0) {
                //找到其所有孩子并放入列表
                List<BaseLabelAndValueVO> childrenList = new ArrayList<>();
                for (Category cc : categories) {
                    if (cc.getPid().equals(cf.getId())) {
                        childrenList.add(new BaseLabelAndValueVO(cc.getId(), cc.getName()));
                    }
                }
                tempCategoryList.add(new GoodsCategoryVO(cf.getId(), cf.getName(), childrenList));
            }
            goodsCatAndBrandVO.setCategoryList(tempCategoryList);
        }
        return goodsCatAndBrandVO;
    }

    //获取商品详情
    @Override
    public GoodsDetailVO queryGoodsDetail(Integer id) {
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        //goods应当能查出当时所属的category,即便已经过时
        Category category = categoryMapper.selectByPrimaryKey(goods.getCategoryId());
        Integer[] categoryIds = {category.getPid(), category.getId()};

        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        goodsAttributeExample.createCriteria().andGoodsIdEqualTo(goods.getId()).andDeletedEqualTo(false);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(goodsAttributeExample);

        GoodsProductExample goodsProductExample = new GoodsProductExample();
        goodsProductExample.createCriteria().andGoodsIdEqualTo(goods.getId()).andDeletedEqualTo(false);
        List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(goodsProductExample);

        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        goodsSpecificationExample.createCriteria().andGoodsIdEqualTo(goods.getId()).andDeletedEqualTo(false);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(goodsSpecificationExample);

        return new GoodsDetailVO(categoryIds, goods, goodsAttributes, goodsSpecifications, goodsProducts);
    }

    //获取商品列表
    @Override
    public BaseData queryGoodsList(Integer page, Integer limit, String goodsSn, String name, String sort, String order) {
        GoodsExample goodsExample = new GoodsExample();
        //不正常参数清洗
        if ("".equals(goodsSn)) goodsSn = null;
        if ("".equals(name)) name = null;
        //名称有模糊查询，编号是精准查询
        if (goodsSn == null && name == null) {
            goodsExample.createCriteria().andDeletedEqualTo(false);
        } else if (goodsSn == null) {
            goodsExample.createCriteria().andDeletedEqualTo(false).andNameLike("%" + name + "%");
        } else if (name == null) {
            goodsExample.createCriteria().andDeletedEqualTo(false).andGoodsSnEqualTo(goodsSn);
        } else
            goodsExample.createCriteria().andDeletedEqualTo(false).andGoodsSnEqualTo(goodsSn).andNameLike("%" + name + "%");

        goodsExample.setOrderByClause(sort + " " + order);  //sort
        PageHelper.startPage(page, limit);
        List<Goods> goodsList = goodsMapper.selectByExampleWithBLOBs(goodsExample);
        //通过pageHelper得到Total
        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
        long total = pageInfo.getTotal();
        return new BaseData(goodsList, total);
    }
}
