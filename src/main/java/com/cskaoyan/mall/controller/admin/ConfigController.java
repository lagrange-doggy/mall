package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.vo.*;
import com.cskaoyan.mall.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ZhidaFeng on 2020/6/27
 **/
@RestController
@RequestMapping("admin/config")
public class ConfigController {

    @Autowired
    ConfigService configService;

    @RequestMapping(value = "mall",method = RequestMethod.GET)
    public BaseRespVo queryMallConfigGet(){
        Map<String,Object> mallConfig = configService.getMallConfig();
        BaseRespVo baseRespVo = new BaseRespVo();
        return baseRespVo.ok(mallConfig);
    }

    @RequestMapping(value = "mall",method = RequestMethod.POST)
    public BaseRespVo updateMallConfigPost(@RequestBody ConfigMallVo configMallVo){
        configService.updateMallConfigPost(configMallVo);
        BaseRespVo baseRespVo = new BaseRespVo();
        return baseRespVo.ok();
    }

    @RequestMapping(value = "express",method = RequestMethod.GET)
    public BaseRespVo queryExpressConfigGet(){
        Map<String,Object> map=configService.getExpressConfig();
        BaseRespVo baseRespVo = new BaseRespVo();
        return baseRespVo.ok(map);
    }

    @RequestMapping(value = "express",method = RequestMethod.POST)
    public BaseRespVo updateExpressConfigPost(@RequestBody ConfigExpressVo configExpress){
        configService.updateExpressConfigPost(configExpress);
        BaseRespVo baseRespVo = new BaseRespVo();
        return baseRespVo.ok();
    }

    @RequestMapping(value = "order", method = RequestMethod.GET)
    public BaseRespVo queryOrderConfigGet(){
        ConfigOrderVo configOrderVo = configService.getOrderConfig();
        BaseRespVo baseRespVo = new BaseRespVo();
        return baseRespVo.ok(configOrderVo);
    }

    @RequestMapping(value = "order",method = RequestMethod.POST)
    public BaseRespVo updateOrderConfigPost(@RequestBody ConfigOrderVo configOrderVo){
        configService.updateOrderConfig(configOrderVo);
        BaseRespVo baseRespVo = new BaseRespVo();
        return baseRespVo.ok();
    }

    @RequestMapping(value = "wx",method = RequestMethod.GET)
    public BaseRespVo queryWxConfigGet(){
        ConfigWxVo configWxVo = configService.getWxConfig();
        BaseRespVo baseRespVo = new BaseRespVo();
        return baseRespVo.ok(configWxVo);
    }

    @RequestMapping(value = "wx",method = RequestMethod.POST)
    public BaseRespVo updateWxConfigPost(@RequestBody ConfigWxVo configWxVo){
        configService.updateWxConfig(configWxVo);
        BaseRespVo baseRespVo = new BaseRespVo();
        return baseRespVo.ok();
    }

}
