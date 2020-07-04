package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Address;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.bo.AddressCreateBo;
import com.cskaoyan.mall.bean.vo.AddressDetailVo;
import com.cskaoyan.mall.bean.vo.AddressVo;

import java.util.List;

public interface AddressService {
    BaseData queryAddress(Integer page, Integer limit, String name, Integer userId, String sort, String order);

    List<AddressVo> queryAddressByUserId();

    AddressDetailVo queryAddressDetailVo(Integer id);

    void saveAddress(AddressCreateBo createBo);

    void deleteAddress(Integer id);
}
