package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.*;
import com.cskaoyan.mall.service.WxCartService;
import com.cskaoyan.mall.utils.UserUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ZhidaFeng on 2020/6/30
 **/
@Service
public class WxCartServiceImpl implements WxCartService {

    @Autowired
    CartMapper cartMapper;

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    ConfigMallMapper configMallMapper;
    @Autowired
    CouponMapper couponMapper;
    @Override
    public CartDate getCartDate() {
        //获取当前登陆用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        int userId = user.getId();
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<Cart> carts = cartMapper.selectByExample(cartExample);

        CartTotal cartTotal = cartMapper.getCartTotal(userId);

        CartDate cartDate = new CartDate();
        cartDate.setCartList(carts);
        cartDate.setCartTotal(cartTotal);

        return cartDate;

    }

    @Override
    public CartDate updateChecked(List productIds, boolean isChecked) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer id = user.getId();
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andProductIdIn(productIds).andUserIdEqualTo(id);

        Cart cart = new Cart();
        cart.setChecked(isChecked);

        cartMapper.updateByExampleSelective(cart, cartExample);
        CartDate cartDate = this.getCartDate();
        return cartDate;
    }

    @Override
    public int cartAdd(Integer goodsId, Integer number, Integer productId) throws Exception {
        Cart cart = new Cart();
        cart.setGoodsId(goodsId);
        cart.setProductId(productId);
        Integer userId = UserUtil.getUserId();
        cart.setUserId(userId);
        cart.setNumber(number.shortValue());
        //如果有购物车有商品直接增加number
        int i = cartMapper.updateByAddnumber(cart);
        if (i>0){
            return cartMapper.countCart(userId);
        }

        insertCart(cart);
        return cartMapper.countCart(userId);
    }

    private Cart insertCart(Cart cart){

        Goods goods = goodsMapper.selectByPrimaryKey(cart.getGoodsId());
        cart.setGoodsSn(goods.getGoodsSn());
        cart.setGoodsName(goods.getName());
        GoodsProduct goodsProduct = goodsProductMapper.selectByPrimaryKey(cart.getProductId());
        cart.setPrice(goodsProduct.getPrice());
        cart.setSpecifications(goodsProduct.getSpecifications());
        cart.setPicUrl(goodsProduct.getUrl());
        cart.setDeleted(false);
        cartMapper.insertSelective(cart);
        return cart;
    }

    @Override
    public void updateCart(Integer goodsId, Integer id, Integer number, Integer productId) throws Exception {
        Cart cart = new Cart();
        cart.setGoodsId(goodsId);
        cart.setId(id);
        short numbershort = number.shortValue();
        cart.setNumber(numbershort);
        cart.setProductId(productId);
        Integer userId = UserUtil.getUserId();
        cart.setUserId(userId);
        cartMapper.updateByPrimaryKeySelective(cart);
    }

    @Override
    public int cartGoodsCount() throws Exception {
        Integer userId = UserUtil.getUserId();
        int countCart = cartMapper.countCart(userId);
        return countCart;
    }

//    private HashMap<Integer, Cart> cartFastAdd = new HashMap<>();
    @Override
    public int cartFastAdd(Integer goodsId, Integer number, Integer productId) throws Exception {
        Cart cart = new Cart();
        cart.setGoodsId(goodsId);
        cart.setNumber(number.shortValue());
        cart.setProductId(productId);
        Integer userId = UserUtil.getUserId();
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andGoodsIdEqualTo(goodsId).andUserIdEqualTo(userId).andProductIdEqualTo(productId).andDeletedEqualTo(false);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        if (carts.size()>0){
            return carts.get(0).getId();
        }
        this.cartAdd(goodsId,number,productId);
        int cartId = cartMapper.selectCartIdByGoodIdProductIdUserId(userId, goodsId, productId);
        return cartId;
//        CartExample cartExample = new CartExample();
//        cartExample.createCriteria().andProductIdEqualTo(productId).andGoodsIdEqualTo(goodsId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
//        Cart carts = (Cart) cartMapper.selectByExample(cartExample);
//        if (carts!=null) {
//            return carts.getId();
//        }else {
//            Cart cart1 = insertCart(cart);
//            return cart1.getId();
//        }
    }

    @Override
    public CartDate deletedCart(List<Integer> productIds) throws Exception {
        Integer userId = UserUtil.getUserId();

        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andProductIdIn(productIds).andUserIdEqualTo(userId);
        int i = cartMapper.deleteByExample(cartExample);
        return this.getCartDate();
    }

    @Override
    public Map cartCheckOut(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId) throws Exception {
        Map map = new HashMap();
        map.put("grouponPrice", 0);
        map.put("grouponRulesId", 0);
        Integer userId = UserUtil.getUserId();
        Address address = this.getAddress(userId, addressId);
        map.put("checkedAddress",address);
        map.put("addressId", address != null ? address.getId() : null);
        List<GoodsChecked> goodsCheckeds = new ArrayList<>();
        Cart cart = cartMapper.selectByPrimaryKey(cartId);

        if (cartId==0){
            goodsCheckeds = this.queryGoodsChecked(userId);
        }else {
            GoodsChecked goodsChecked = cartMapper.getGoodsCheckedByGoodsIdAndProductId(cart.getGoodsId(), cart.getProductId());
//            Cart cart = cartFastAdd.get(userId);
//            cartFastAdd.remove(userId);
//            GoodsChecked goodsChecked = cartMapper.getGoodsCheckedByGoodsIdAndProductId(cart.getGoodsId(),cart.getProductId());
            goodsChecked.setNumber(cart.getNumber().intValue());
            goodsCheckeds.add(goodsChecked);
        }
        map.put("checkedGoodsList", goodsCheckeds);

        //商品价格
        double price = this.sumPrice(goodsCheckeds);
        int [] freight = this.getFreight();
        //判断是否包邮
        if (price > freight[0]){
            freight[1] = 0;
        }
        map.put("freightPrice", freight[1]);
        map.put("goodsTotalPrice", price);
        //优惠价格
        double discount =0;
        double actualPrice = price + freight[1];
        if (couponId>0){
            Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
            BigDecimal min = coupon.getMin();
            if (price > min.doubleValue()){
                BigDecimal bigDecimal = coupon.getDiscount();
                discount = bigDecimal.doubleValue();
                actualPrice = price - discount + freight[1];
            }

        }

        List<Coupon> coupons = couponMapper.selectByExample(new CouponExample());

        map.put("availableCouponLength",coupons.size());
        map.put("couponId", couponId);
        map.put("couponPrice", discount);
        map.put("actualPrice", actualPrice);
        map.put("orderTotalPrice",actualPrice);
        return map;
    }

    private int[] getFreight() {
        ConfigMallExample freightMin = new ConfigMallExample();
        freightMin.createCriteria().andKeyNameEqualTo("cskaoyan_mall_express_freight_min");
        List<ConfigMall> configMallFreightMin = configMallMapper.selectByExample(freightMin);

        ConfigMallExample freightValue = new ConfigMallExample();
        freightValue.createCriteria().andKeyNameEqualTo("cskaoyan_mall_express_freight_value");
        List<ConfigMall> configMallFreightValue = configMallMapper.selectByExample(freightValue);
        return new int[]{
                Integer.valueOf(configMallFreightMin.get(0).getKeyValue()),
                Integer.valueOf(configMallFreightValue.get(0).getKeyValue())};
    }

    private double sumPrice(List<GoodsChecked> goodsCheckeds) {
        double price = 0;
        for (GoodsChecked goodsChecked : goodsCheckeds) {
            price += (goodsChecked.getPrice()*goodsChecked.getNumber());
        }
        return price;
    }


    private Address getAddress(Integer userId, Integer addressId) {
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria().andUserIdEqualTo(userId);
        // addressId为0时查询该用户的默认地址
        if (addressId == 0) {
            criteria.andDeletedEqualTo(true);
        } else {
            criteria.andIdEqualTo(addressId);
        }
        List<Address> addresses = addressMapper.selectByExample(addressExample);
        return addresses.size() == 0 ? null : addresses.get(0);
    }

    private List<GoodsChecked> queryGoodsChecked(Integer userId) {
        List<GoodsChecked> goodsCheckeds = cartMapper.queryGoodsCheckedByUserId(userId);
        return goodsCheckeds;
    }


}
