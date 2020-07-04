package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.vo.BrandAddVO;
import com.cskaoyan.mall.config.MyException;
import com.cskaoyan.mall.constants.MallConstant;
import com.cskaoyan.mall.mapper.BrandMapper;
import com.cskaoyan.mall.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;


    @Override
    public boolean delete(Brand brand) {
        brand.setDeleted(true);
        brand.setUpdateTime(new Date());
        int i = brandMapper.updateByPrimaryKeySelective(brand);
        return i == 1;
    }

    @Override
    public boolean updateByBrand(Brand brand) {
        brand.setUpdateTime(new Date());
        int i = brandMapper.updateByPrimaryKeySelective(brand);
        return i == 1;
    }

    @Override
    public Map<String, Object> queryBrandPageList(Integer page, Integer limit, Integer id, String name, String sort, String order) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(page, limit);
        List<Brand> brands = brandMapper.queryBrandPageList(id, name, sort, order);
        PageInfo pageInfo = new PageInfo(brands);
        long total = pageInfo.getTotal();
        map.put("total", total);
        map.put("items", brands);
        return map;
    }

    @Override
    public Brand add(BrandAddVO brandAddVO) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandAddVO, brand);
        brand.setAddTime(new Date());
        brand.setUpdateTime(new Date());
        brand.setDeleted(false);
        try {
            brandMapper.insert(brand);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new MyException(605, MallConstant.BRAND_ADD_ERROR);
            }
            if (e instanceof DataIntegrityViolationException){
                throw new MyException(605,MallConstant.BRAND_ADD_OTHER_ERROR);
            }
        }
        return brand;
    }
}