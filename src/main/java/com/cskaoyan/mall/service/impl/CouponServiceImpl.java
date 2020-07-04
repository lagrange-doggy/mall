package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.vo.CouponUserVo;
import com.cskaoyan.mall.mapper.CartMapper;
import com.cskaoyan.mall.mapper.CouponMapper;
import com.cskaoyan.mall.mapper.CouponUserMapper;
import com.cskaoyan.mall.service.CouponService;
import com.cskaoyan.mall.utils.DateModifyUtils;
import com.cskaoyan.mall.utils.GenSerialUtils;
import com.cskaoyan.mall.utils.UserUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/26 20:53
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CouponUserMapper couponUserMapper;

    @Autowired
    CartMapper cartMapper;

    @Transactional
    @Override
    public int receiveCoupon(Integer couponId) throws Exception {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        if (coupon.getTotal() <= 0) return 0;
        coupon.setTotal(coupon.getTotal() - 1);
        Date date = new Date();
        CouponUser couponUser = new CouponUser();
        try {
            couponUser.setUserId(UserUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        couponUser.setCouponId(couponId);
        couponUser.setAddTime(date);
        couponUser.setStartTime(date);
        couponUser.setDeleted(false);
        couponUser.setStatus((short)0);
        couponUser.setUpdateTime(date);
        Date date1 = DateModifyUtils.addDays(coupon.getDays());
        couponUser.setEndTime(date1);
        createCouponUser(couponUser);
        return couponMapper.updateByPrimaryKey(coupon);
    }

    @Override
    public void createCouponUser(CouponUser couponUser) {
        couponUserMapper.insert(couponUser);
    }

    @Override
    @Transactional
    public int exchangeCoupon(String code) {
        Date date = new Date();
        CouponExample couponExample = new CouponExample();
        couponExample.createCriteria().andCodeEqualTo(code.toString());
        List<Coupon> list = couponMapper.selectByExample(couponExample);
        int total = 0;
        for (Coupon coupon : list) {
            if (coupon.getTotal() <= 0) continue;
            coupon.setTotal(coupon.getTotal() - 1);
            total += couponMapper.updateByPrimaryKey(coupon);
            CouponUser couponUser = new CouponUser();
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            couponUser.setUserId(user.getId());
            couponUser.setCouponId(coupon.getId());
            Short timeType = coupon.getTimeType();
            if (timeType == 0) {
                couponUser.setStartTime(date);
                couponUser.setEndTime(DateModifyUtils.addDays(coupon.getDays()));
            } else {
                couponUser.setStartTime(coupon.getStartTime());
                couponUser.setEndTime(coupon.getEndTime());
            }
            Short status = coupon.getStatus();
            if (couponUser.getEndTime().getTime() > date.getTime()) {
                couponUser.setStatus((short)0);
            } else couponUser.setStatus((short)1);
            couponUser.setDeleted(false);
            createCouponUser(couponUser);
        }
        return total;
    }

    @Override
    public List<Coupon> queryCoupon(Integer cartId, Integer grouponRulesId) {
//        Cart cart = cartMapper.selectByPrimaryKey(cartId);
        Integer userId = UserUtil.getUserId();
        CouponUserExample couponUserExample = new CouponUserExample();
        couponUserExample.createCriteria().andUserIdEqualTo(userId).andEndTimeGreaterThan(new Date()).andDeletedEqualTo(false);
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
        ArrayList<Coupon> coupons = new ArrayList<>();
        for (CouponUser couponUser : couponUsers) {
            Coupon coupon = couponMapper.selectByPrimaryKey(couponUser.getCouponId());
            coupon.setStartTime(couponUser.getStartTime());
            coupon.setEndTime(couponUser.getEndTime());
            coupons.add(coupon);
        }
        return coupons;
    }

    @Override
    public WxData queryCoupons(Short status, Integer page, Integer size) {
        ArrayList<CouponUserVo> cuVos = new ArrayList<>();
        Date date = new Date();
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        // 根据userId从coupon_user查询 status分情况  start_time<now end_time>now desc
        List<CouponUser> couponUsers = couponUserMapper.selectCouponUserByStatus(user.getId(), status);

        //根据coupon_id从coupon查询
        for (CouponUser couponUser : couponUsers) {
            CouponUserVo couponUserVo = new CouponUserVo();
            Coupon coupon = couponMapper.selectByPrimaryKey(couponUser.getCouponId());
            couponUserVo.setName(coupon.getName());
            couponUserVo.setDesc(coupon.getDesc());
            couponUserVo.setTag(coupon.getTag());
            couponUserVo.setMin(coupon.getMin().doubleValue());
            couponUserVo.setDiscount(coupon.getDiscount().doubleValue());
            couponUserVo.setStartTime(couponUser.getStartTime());
            couponUserVo.setEndTime(couponUser.getEndTime());
            cuVos.add(couponUserVo);
        }
        PageInfo<CouponUser> pageInfo = new PageInfo<>(couponUsers);
        long total = pageInfo.getTotal();
        return new WxData(cuVos, total);
    }

    @Override
    public WxData queryCoupons(Integer page, Integer size) {
        List<Coupon> coupons = couponMapper.selectCouponByTimeType(1);
        coupons.addAll(couponMapper.selectCouponByTimeType(0));
        PageInfo<Coupon> pageInfo = new PageInfo<>(coupons);
        long total = pageInfo.getTotal();
        return new WxData(coupons, total);
    }

    @Override
    public BaseData queryCoupon(Integer page, Integer limit, Integer couponId, Integer userId, Short status, String sort, String order) {
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        if (status != null) criteria.andStatusEqualTo(status);
        if (couponId != null) criteria.andCouponIdEqualTo(couponId);
        if (userId != null) criteria.andUserIdEqualTo(userId);
        couponUserExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page,limit);
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
        PageInfo<CouponUser> pageInfo = new PageInfo<>(couponUsers);
        long total = pageInfo.getTotal();
        return new BaseData(couponUsers, total);
    }

    @Override
    public Coupon queryCouponById(Integer id) {
        Coupon coupon = couponMapper.selectByPrimaryKey(id);
        return coupon;
    }

    @Override
    public void updateCoupon(Coupon coupon) {
        coupon.setUpdateTime(new Date());
        couponMapper.updateByPrimaryKey(coupon);
    }

    @Override
    public void deleteCoupon(Coupon coupon) {
        couponMapper.deleteByPrimaryKey(coupon.getId());
    }

    @Override
    public void createCoupon(Coupon coupon) {
        Date date = new Date();
        coupon.setAddTime(date);
        coupon.setUpdateTime(date);
        coupon.setDeleted(false);
        String code = GenSerialUtils.generateNewCode();
        coupon.setCode(code);
        couponMapper.insert(coupon);
    }

    @Override
    public BaseData queryCoupons(Integer page, String name, Short type, Short status, Integer limit, String sort, String order) {
        CouponExample couponExample = new CouponExample();
        CouponExample.Criteria criteria = couponExample.createCriteria();
        if (name != null) criteria.andNameLike("%"+name+"%");
        if (type != null) criteria.andTypeEqualTo(type);
        if (status != null) criteria.andStatusEqualTo(status);
        couponExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page,limit);
        List<Coupon> coupons = couponMapper.selectByExample(couponExample);
        PageInfo<Coupon> pageInfo = new PageInfo<>(coupons);
        long total = pageInfo.getTotal();
        return new BaseData(coupons, total);
    }

}
