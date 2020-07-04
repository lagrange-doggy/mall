package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.GoodsStatistics;
import com.cskaoyan.mall.bean.OrderStistiscs;
import com.cskaoyan.mall.bean.UserStatistics;
import com.cskaoyan.mall.bean.vo.StatisticalVo;
import com.cskaoyan.mall.service.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ZhidaFeng on 2020/6/27
 **/
@RestController
@RequestMapping("admin/stat")
public class StatisticalController {

    @Autowired
    StatisticalService statisticalService;

    @RequestMapping(value = "user",method = RequestMethod.GET)
    public BaseRespVo<StatisticalVo> queryUserStatistical(){
       StatisticalVo<UserStatistics> statisticalVo = statisticalService.getUserStatistical();
        BaseRespVo<StatisticalVo> BaseRespVo = new BaseRespVo<>();
        return BaseRespVo.ok(statisticalVo);
    }

    @RequestMapping(value = "order",method = RequestMethod.GET)
    public BaseRespVo<StatisticalVo> queryorderStatistical(){
        StatisticalVo<OrderStistiscs> statisticalVo =statisticalService.getOrderStatistical();
        BaseRespVo<StatisticalVo> objectBaseRespVo = new BaseRespVo<>();
        return  objectBaseRespVo.ok(statisticalVo);
    }

    @RequestMapping(value = "goods",method = RequestMethod.GET)
    public BaseRespVo<StatisticalVo> quergoodsStatistical(){
        StatisticalVo<GoodsStatistics> statisticalVo = statisticalService.getGoodsStatical();
        BaseRespVo<GoodsStatistics> baseRespVo = new BaseRespVo<>();
        return  baseRespVo.ok(statisticalVo);
    }

}
