package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.bo.AddressCreateBo;
import com.cskaoyan.mall.bean.vo.AddressDetailVo;
import com.cskaoyan.mall.bean.vo.AddressVo;
import com.cskaoyan.mall.bean.vo.UserAddressVO;
import com.cskaoyan.mall.mapper.AddressMapper;
import com.cskaoyan.mall.mapper.RegionMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.AddressService;
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

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    RegionMapper regionMapper;

    @Override
    public void deleteAddress(Integer id) {
        Address address = addressMapper.selectByPrimaryKey(id);
        address.setUpdateTime(new Date());
        address.setDeleted(true);
        addressMapper.updateByPrimaryKey(address);
    }

    /**
     * id = 0时insert，否则update
     * @param createBo
     */
    @Override
    @Transactional
    public void saveAddress(AddressCreateBo createBo) {
        Address address = new Address();
        Date date = new Date();
        if (createBo.getId() != null && createBo.getId() != 0) address = addressMapper.selectByPrimaryKey(createBo.getId());
        if (createBo.getId() == 0) {
            address.setAddTime(date);
        }
        address.setName(createBo.getName());
        if (createBo.getId() == 0) {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            address.setUserId(user.getId());
        }
        address.setProvinceId(createBo.getProvinceId());
        address.setCityId(createBo.getCityId());
        address.setAreaId(createBo.getAreaId());
        address.setAddress(createBo.getAddress());
        address.setMobile(createBo.getMobile());
        address.setIsDefault(createBo.getIsDefault());
        address.setUpdateTime(date);
        address.setDeleted(false);
        if (createBo.getIsDefault()) {
            addressMapper.updateIsDefaultByUserId(address.getUserId());
        }
        if (createBo.getId() == 0) addressMapper.insert(address);
        else addressMapper.updateByPrimaryKey(address);
    }

    @Override
    public AddressDetailVo queryAddressDetailVo(Integer id) {
        AddressDetailVo detailVo = new AddressDetailVo();
        Address address = addressMapper.selectByPrimaryKey(id);
        String province = regionMapper.selectById(address.getProvinceId());
        String city = regionMapper.selectById(address.getCityId());
        String area = regionMapper.selectById(address.getAreaId());
        detailVo.setIsDefault(address.getIsDefault());
        detailVo.setAreaId(address.getAreaId());
        detailVo.setProvinceId(address.getProvinceId());
        detailVo.setCityId(address.getCityId());
        detailVo.setCityName(city);
        detailVo.setAreaName(area);
        detailVo.setProvinceName(province);
        detailVo.setName(address.getName());
        detailVo.setMobile(address.getMobile());
        detailVo.setId(address.getId());
        detailVo.setAddress(address.getAddress());
        return detailVo;
    }

    @Override
    public List<AddressVo> queryAddressByUserId() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Address> addresses = addressMapper.selectByUserId(user.getId());
        ArrayList<AddressVo> addressVos = new ArrayList<>();
        for (Address address : addresses) {
            String province = regionMapper.selectById(address.getProvinceId());
            String city = regionMapper.selectById(address.getCityId());
            String area = regionMapper.selectById(address.getAreaId());
            AddressVo addressVo = new AddressVo();
            addressVo.setIsDefault(address.getIsDefault());
            addressVo.setDetailedAddress(province + city + area + " " + address.getAddress());
            addressVo.setName(address.getName());
            addressVo.setMobile(address.getMobile());
            addressVo.setId(address.getId());
            addressVos.add(addressVo);
        }
        return addressVos;
    }

    @Override
    public BaseData queryAddress(Integer page, Integer limit, String name, Integer userId, String sort, String order) {
        name = ("".equals(name))?null:name;
        AddressExample addressExample = new AddressExample();
        addressExample.setOrderByClause(sort + " " + order);
        if (userId == null && userId == null){
            addressExample.createCriteria().andDeletedEqualTo(false);
        }else if (userId == null){
            addressExample.createCriteria().andDeletedEqualTo(false).andNameLike("%"+name+"%");
        }else if (name ==null){
            addressExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userId);
        }else addressExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userId).andNameLike("%"+name+"%");

        PageHelper.startPage(page,limit);
        List<Address> address = addressMapper.selectByExample(addressExample);
        List<UserAddressVO> addressVOS = new ArrayList<>();
        PageInfo<Address> pageInfo = new PageInfo<>(address);
        long total = pageInfo.getTotal();
        for (Address a:address){
            String provinceName = regionMapper.selectById(a.getProvinceId());
            String cityName = regionMapper.selectById(a.getCityId());
            String areaName = regionMapper.selectById(a.getAreaId());
            addressVOS.add(new UserAddressVO(a.getId(),a.getName(),a.getUserId(),a.getProvinceId(),a.getCityId(),a.getAreaId(),a.getAddress(),a.getMobile(),a.getIsDefault(),provinceName,cityName,areaName));
        }
        return new BaseData(addressVOS, total);
    }
}