package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.service.WxBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/brand")
public class WxBrandController {
    @Autowired
    WxBrandService wxBrandService;
    @RequestMapping("list")
    public BaseRespVo list(Integer page, Integer size){
        BaseRespVo<Map<String,Object>> response = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();
        List<Brand> brandList = wxBrandService.getBrandList();
        data.put("brandList",brandList);
        data.put("totalPages",brandList.size());
        response.setData(data);
        response.setErrmsg("成功");
        response.setErrno(0);
        return response;
    }
    @RequestMapping("detail")
    public BaseRespVo brandDetail(Integer id) {
        BaseRespVo<Map<String,Object>> response = new BaseRespVo<>();
        Map<String ,Object> data = new HashMap<>();
        try {
            Brand brand = wxBrandService.getBrandById(id);
            data.put("brand",brand);
            response.setData(data);
        }catch (Exception e) {
            e.printStackTrace();
            response.setErrno(500);
            response.setErrmsg("错误");
            return response;
        }
        response.setErrmsg("成功");
        response.setErrno(0);
        return response;
    }
}