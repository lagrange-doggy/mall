package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.MallApplication;
import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.CartDate;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.service.WxCartService;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ZhidaFeng on 2020/6/30
 **/
@RestController
@RequestMapping("wx/cart")
public class WxCartController {

    @Autowired
    WxCartService wxCartService;

    @RequestMapping(value = "index" ,method = RequestMethod.GET)
    public BaseRespVo<CartDate> cartIndex(){

       CartDate cartDate = wxCartService.getCartDate();
        return BaseRespVo.ok(cartDate);
    }

    @RequestMapping(value = "checked" ,method = RequestMethod.POST)
    public BaseRespVo<CartDate> cartChecked(@RequestBody Map map){
        List productIds = (List) map.get("productIds");
        Integer isChecked = (Integer) map.get("isChecked");
        CartDate cartDate = wxCartService.updateChecked(productIds,isChecked==1);
        return BaseRespVo.ok(cartDate);
    }

    @RequestMapping("add")
    public BaseRespVo cartAdd(@RequestBody Map map) throws Exception {
        Integer goodsId = (Integer) map.get("goodsId");
        Integer number = (Integer) map.get("number");
        Integer productId = (Integer) map.get("productId");
        int date = wxCartService.cartAdd(goodsId,number,productId);
        return BaseRespVo.ok(date);
    }

    @RequestMapping("update")
    public BaseRespVo cartUpdate(@RequestBody Map map) throws Exception {
        Integer goodsId = (Integer) map.get("goodsId");
        Integer id = (Integer) map.get("id");
        Integer number = (Integer) map.get("number");
        Integer productId = (Integer) map.get("productId");

        wxCartService.updateCart(goodsId,id,number,productId);
        return BaseRespVo.ok();
    }

    @RequestMapping("goodscount")
    public BaseRespVo cartGoodsCount() throws Exception {
        int count = wxCartService.cartGoodsCount();
        return BaseRespVo.ok(count);
    }

    @RequestMapping("delete")
    public BaseRespVo deletedCart(@RequestBody Map map) throws Exception {
        List<Integer> productIds = (List<Integer>) map.get("productIds");
        CartDate cartDate = wxCartService.deletedCart(productIds);
        return BaseRespVo.ok(cartDate);
    }
    @RequestMapping("fastadd")
    public BaseRespVo cartFastAdd(@RequestBody Map map) throws Exception {
        Integer goodsId = (Integer) map.get("goodsId");
        Integer number = (Integer) map.get("number");
        Integer productId = (Integer) map.get("productId");
         Integer id = wxCartService.cartFastAdd(goodsId,number,productId);
         return BaseRespVo.ok(id);
    }

    @RequestMapping("checkout")
    public BaseRespVo cartCheckOut(Integer cartId,Integer addressId, Integer couponId,Integer grouponRulesId) throws Exception {
        Map map = wxCartService.cartCheckOut(cartId,addressId,couponId,grouponRulesId);
        return BaseRespVo.ok(map);
    }
}
