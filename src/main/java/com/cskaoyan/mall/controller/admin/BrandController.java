package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.vo.BrandAddVO;
import com.cskaoyan.mall.service.BrandService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("admin/brand")
@CrossOrigin
public class BrandController {

    @Autowired
    BrandService brandService;

    @RequiresPermissions(value = "admin:brand:list")
    @RequestMapping("list")
    public BaseRespVo brandPageList(@RequestParam("page") Integer page,
                            @RequestParam("limit") Integer limit,
                            @RequestParam(value = "id",required = false) Integer id,
                            @RequestParam(value = "name",required =false) String name,
                            @RequestParam("sort") String sort,
                            @RequestParam("order") String order){
        Map<String,Object> map=brandService.queryBrandPageList(page,limit,id,name,sort,order);
        return BaseRespVo.ok(map);

    }

    @RequiresPermissions(value = "admin:brand:delete")
    @RequestMapping("delete")
    public BaseRespVo brandDelete(@RequestBody Brand brand){
        boolean flag= brandService.delete(brand);
        if (flag) return BaseRespVo.ok();
        return BaseRespVo.err();
    }

    @RequiresPermissions(value = "admin:brand:update")
    @PostMapping("update")
    public BaseRespVo brandUpdate(@RequestBody Brand brand){
        boolean flag= brandService.updateByBrand(brand);
        if (flag) return BaseRespVo.ok();
        return BaseRespVo.err();
    }

    @RequiresPermissions(value = "admin:brand:create")
    @PostMapping("create")
    public BaseRespVo addBrand(@RequestBody BrandAddVO brandAddVO){
        Brand brand= brandService.add(brandAddVO);
        return BaseRespVo.ok(brand);
    }

}