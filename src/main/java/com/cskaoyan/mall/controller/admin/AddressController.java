package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer limit, String name, Integer userId, String sort, String order){
        BaseData baseData = addressService.queryAddress(page, limit, name, userId, sort, order);
        return BaseRespVo.ok(baseData);
    }
}
