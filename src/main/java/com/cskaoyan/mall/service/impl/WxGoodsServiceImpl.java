package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.bo.GoodsCommentBo;
import com.cskaoyan.mall.bean.vo.*;
import com.cskaoyan.mall.bean.vo.wxHomeIndexVO.WxIndexGoodsVO;
import com.cskaoyan.mall.mapper.*;
import com.cskaoyan.mall.service.WxGoodsService;
import com.cskaoyan.mall.utils.UserUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: GZ
 * @description: wx goods service impl
 */
@Service
public class WxGoodsServiceImpl implements WxGoodsService {

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsAttributeMapper goodsAttributeMapper;
    @Autowired
    GoodsSpecificationMapper goodsSpecificationMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    IssueMapper issueMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;
    @Autowired
    CollectMapper collectMapper;


    //wx goods relate
    @Override
    public List<WxIndexGoodsVO> querySameCategoryAndNew(Integer id) {
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andDeletedEqualTo(false).andIsNewEqualTo(false)   //本来应该是推新品，奈何数据库里没新品
                .andCategoryIdEqualTo(goods.getCategoryId()).andIdNotEqualTo(goods.getId()).andIsOnSaleEqualTo(true);
        List<Goods> goodsList = goodsMapper.selectByExampleWithBLOBs(goodsExample);
        List<WxIndexGoodsVO> goodsVOList = new ArrayList<>();
        for (Goods g : goodsList) {
            goodsVOList.add(new WxIndexGoodsVO(g));
        }
        return goodsVOList;
    }

    /**
     * By Fzd
     *
     * @param goodsid
     * @return map
     */

    @Override
    public Map<String, Object> wxGoodsDetail(Integer goodsid) {

        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIdEqualTo(goodsid);
        List<Goods> goodsList = goodsMapper.selectByExampleWithBLOBs(goodsExample);
        Goods info = goodsList.get(0);

        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        goodsAttributeExample.createCriteria().andIdEqualTo(goodsid);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(goodsAttributeExample);

        Integer brandId = info.getBrandId();
        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria().andIdEqualTo(brandId);
        List<Brand> brandsList = brandMapper.selectByExample(brandExample);
        Brand brand = new Brand();
        if (brandsList != null && brandsList.size() > 0) {
            brand = brandsList.get(0);
        }

        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andValueIdEqualTo(goodsid);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        int count = comments.size();

        List<GoodsComment> data = new ArrayList<>();
        int i = 0;
        for (Comment comment : comments) {
            if (i < 3) {
                GoodsComment goodsComment = new GoodsComment();
                goodsComment.setAddTime(comment.getAddTime());
                Integer userId = comment.getUserId();
                UserExample userExample = new UserExample();
                userExample.createCriteria().andIdEqualTo(userId);
                List<User> users = userMapper.selectByExample(userExample);
                User user = new User();
                if (users != null && users.size() > 0) {
                    user = users.get(0);
                }
                goodsComment.setAvatar(user.getAvatar());
                goodsComment.setContent(comment.getContent());
                goodsComment.setId(comment.getId());
                goodsComment.setNickname(user.getNickname());
                goodsComment.setPicList(comment.getPicUrls());
                data.add(goodsComment);
                i++;
            }
        }
        Map<String, Object> comment = new HashMap<>();
        comment.put("count", count);
        comment.put("data", data);

        GrouponRulesExample rulesExample = new GrouponRulesExample();
        rulesExample.createCriteria().andGoodsIdEqualTo(goodsid);
        List<GrouponRules> groupon = grouponRulesMapper.selectByExample(rulesExample);

        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria().andDeletedEqualTo(false);
        List<Issue> issue = issueMapper.selectByExample(issueExample);

        GoodsProductExample goodsProductExample = new GoodsProductExample();
        goodsProductExample.createCriteria().andGoodsIdEqualTo(goodsid).andDeletedEqualTo(false);
        List<GoodsProduct> productList = goodsProductMapper.selectByExample(goodsProductExample);

        String shareImage = info.getShareUrl();

        GoodsSpecificationExample example2 = new GoodsSpecificationExample();
        example2.createCriteria().andGoodsIdEqualTo(goodsid);
        List<GoodsSpecification> specifications = goodsSpecificationMapper.selectByExample(example2);
        List<String> specificationName = goodsSpecificationMapper.selectSpecificationNames(goodsid);
        List<SpecificationBean> specificationList = new LinkedList<>();
        for (String s : specificationName) {
            SpecificationBean specificationBean = new SpecificationBean();
            specificationBean.setName(s);
            for (GoodsSpecification specification : specifications) {
                if (s.equals(specification.getSpecification())) {
                    specificationBean.getValueList().add(specification);
                }
            }
            specificationList.add(specificationBean);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("attribute", goodsAttributes);
        map.put("brand", brand);
        map.put("comment", comment);
        map.put("groupon", groupon);
        map.put("info", info);
        map.put("issue", issue);
        map.put("productList", productList);
        map.put("shareImage", shareImage);
        map.put("specificationList", specificationList);
        map.put("userHasCollect", 0);
        return map;
    }

    // wx goods detail
    @Override
    public WxGoodsDetailVO queryGoodsDetail(Integer id) {
        Goods goodsInfo = goodsMapper.selectByPrimaryKey(id);

        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        goodsAttributeExample.createCriteria().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(goodsAttributeExample);

        Brand brand = brandMapper.selectByPrimaryKey(goodsInfo.getBrandId());
        if (brand == null) brand = new Brand();

        List<WxGoodsDetailCommentDataVO> data = commentMapper.selectCommentAndUserByGoodsId(id, 0, true); //0表示为商品评论
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andDeletedEqualTo(false).andValueIdEqualTo(id);
        int count = (int) commentMapper.countByExample(commentExample);
        WxGoodsBaseDataAndCountVO comments = new WxGoodsBaseDataAndCountVO(count, data);

        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria().andDeletedEqualTo(false);
        List<Issue> issues = issueMapper.selectByExample(issueExample);

        GoodsProductExample goodsProductExample = new GoodsProductExample();
        goodsProductExample.createCriteria().andDeletedEqualTo(false).andGoodsIdEqualTo(id);
        List<GoodsProduct> goodsProductList = goodsProductMapper.selectByExample(goodsProductExample);

        List<WxGoodsDetailSpecificationVO> specificationList = new ArrayList<>();
        String[] names = goodsSpecificationMapper.queryNameOfSpecByGoodsId(id);
        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        for (String n : names) {
            goodsSpecificationExample.clear();
            goodsSpecificationExample.createCriteria().andDeletedEqualTo(false).andGoodsIdEqualTo(id).andSpecificationEqualTo(n);
            List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(goodsSpecificationExample);
            specificationList.add(new WxGoodsDetailSpecificationVO(n, goodsSpecifications));
        }

        String shareImage = goodsInfo.getShareUrl();
        //userHasCollect 判断是否被收藏--type=0,deleted=0,userId,valueId
        Integer userHasCollect = 0;
        if (SecurityUtils.getSubject().getPrincipal() != null) {
            int userId = UserUtil.getUserId();
            Integer i = collectMapper.queryHasCollectByTypeAndUserId(userId, id);
            if (i != null) userHasCollect = 1;
        }
        List<Group> groupon = new ArrayList<>();
        return new WxGoodsDetailVO(goodsAttributes, brand, comments, groupon, goodsInfo, issues, goodsProductList, shareImage, specificationList, userHasCollect);
    }

    //get goodsList 3参/6参/9参
    @Override
    public WxGoodsListByCategoryVO queryGoodsListByCategoryId(Integer categoryId, Integer page
            , Integer size, String sort, String order, String keyword, Boolean isHot, Boolean isNew, Integer brandId) {
        GoodsExample goodsExample = new GoodsExample();
        CategoryExample categoryExample = new CategoryExample();
        if (sort == null && order == null && keyword == null && isHot == null && isNew == null && brandId == null) {  //从首页分类进入
            goodsExample.createCriteria().andCategoryIdEqualTo(categoryId).andDeletedEqualTo(false).andIsOnSaleEqualTo(true);
        } else if (isHot == null && isNew == null && brandId == null) {  //从keywords搜索进 c
            goodsExample.setOrderByClause(sort + " " + order);
            goodsExample.createCriteria().andDeletedEqualTo(false).andIsOnSaleEqualTo(true).andNameLike("%" + keyword + "%");
        } else if (isHot != null && isNew == null && brandId == null) {
            goodsExample.setOrderByClause(sort + " " + order);//热卖 c
            goodsExample.createCriteria().andDeletedEqualTo(false).andIsOnSaleEqualTo(true).andIsHotEqualTo(true);
        } else if (isHot == null && isNew != null && brandId == null) {//新品 c
            goodsExample.setOrderByClause(sort + " " + order);
            goodsExample.createCriteria().andDeletedEqualTo(false).andIsOnSaleEqualTo(true).andIsNewEqualTo(true);
        } else if (isHot == null && isNew == null && brandId != null) {    //商标
            goodsExample.createCriteria().andIsOnSaleEqualTo(true).andDeletedEqualTo(false).andBrandIdEqualTo(brandId);
        }
        PageHelper.startPage(page, size);
        List<Goods> tempGoodsList = goodsMapper.selectByExampleWithBLOBs(goodsExample);
        PageInfo<Goods> pageInfo = new PageInfo<>(tempGoodsList);
        int count = (int) pageInfo.getTotal();
        List<Integer> categoriesOfcurrGoodsList = new ArrayList<>();
        List<WxIndexGoodsVO> goodsList = new ArrayList<>();
        for (Goods g : tempGoodsList) { //转化，拿到当前所有类别
            if (categoryId == null || categoryId == 0){
                goodsList.add(new WxIndexGoodsVO(g));
            }else {
                if (categoryId.equals(g.getCategoryId())){
                    goodsList.add(new WxIndexGoodsVO(g));
                }
            }
            categoriesOfcurrGoodsList.add(g.getCategoryId());
        }
        List<Category> filterCategoryList = new ArrayList<>();
        if (categoriesOfcurrGoodsList.size() != 0)
            filterCategoryList = categoryMapper.queryFilterCategoryList(categoriesOfcurrGoodsList);
        return new WxGoodsListByCategoryVO(count, filterCategoryList, goodsList);
    }


    //get categories
    @Override
    public WxGoodsCategoriesVO queryGoodsCategoriesById(Integer id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        CategoryExample categoryExample = new CategoryExample();
        if (category.getPid() == 0) {
            //判参数 一/二级id
            categoryExample.createCriteria().andPidEqualTo(id).andDeletedEqualTo(false);
            categoryExample.setOrderByClause("id");
            List<Category> brotherCategory = categoryMapper.selectByExample(categoryExample);
            return new WxGoodsCategoriesVO(brotherCategory, brotherCategory.get(0), category);
        } else {
            Category parentCategory = categoryMapper.selectByPrimaryKey(category.getPid());
            categoryExample.createCriteria().andPidEqualTo(category.getPid()).andDeletedEqualTo(false);
            List<Category> brotherCategory1 = categoryMapper.selectByExample(categoryExample);
            return new WxGoodsCategoriesVO(brotherCategory1, category, parentCategory);
        }
    }

    //count sale goods
    @Override
    public Long countSaleGoods() {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andDeletedEqualTo(false).andIsOnSaleEqualTo(true);
        long count = goodsMapper.countByExample(goodsExample);
        return count;
    }
}
