package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.service.WxCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/catalog")
public class WxCatalogController {
    @Autowired
    WxCatalogService wxCatalogService;

    @RequestMapping("current")
    public BaseRespVo current(Integer id) {
        BaseRespVo<Map<String,Object>> response = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();
        try {
            Category currentCategory = wxCatalogService.getCategoryById(id);
            List<Category> currentSubCategory = wxCatalogService.getSubCategoryByPid(currentCategory.getId());
            data.put("currentCategory",currentCategory);
            data.put("currentSubCategory",currentSubCategory);
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


    @RequestMapping("index")
    public BaseRespVo index() {
        BaseRespVo<Map<String,Object>> response = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        try {
            List<Category> categoryList = wxCatalogService.getMainCategoryList();
            if (categoryList.size() > 0) {
                Category currentCategory = categoryList.get(0);
                List<Category> currentSubCategory = wxCatalogService.getSubCategoryByPid(currentCategory.getId());
                data.put("currentCategory",currentCategory);
                data.put("currentSubCategory",currentSubCategory);
            }
            data.put("categoryList",categoryList);
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
