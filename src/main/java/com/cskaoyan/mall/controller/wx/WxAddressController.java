package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.bo.AddressCreateBo;
import com.cskaoyan.mall.bean.vo.AddressDetailVo;
import com.cskaoyan.mall.bean.vo.AddressVo;
import com.cskaoyan.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/6/30 18:41
 */
@RestController
@RequestMapping("wx/address")
public class WxAddressController {

    @Autowired
    AddressService addressService;

    @RequestMapping("list")
    public BaseRespVo list() {
        List<AddressVo> list = addressService.queryAddressByUserId();
        return BaseRespVo.ok(list);
    }

    @RequestMapping("detail")
    public BaseRespVo detail(Integer id) {
        AddressDetailVo detailVo = addressService.queryAddressDetailVo(id);
        return BaseRespVo.ok(detailVo);
    }

    @RequestMapping("save")
    public BaseRespVo save(@RequestBody AddressCreateBo createBo) {
        addressService.saveAddress(createBo);
        return BaseRespVo.ok(createBo.getId());
    }

    @RequestMapping("delete")
    public BaseRespVo delete(@RequestBody Map<String, Integer> map) {
        Integer id = map.get("id");
        addressService.deleteAddress(id);
        return BaseRespVo.ok();
    }
}
