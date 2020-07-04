package com.cskaoyan.mall.controller.admin;


import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.vo.CategoryOneVO;
import com.cskaoyan.mall.service.CategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequiresPermissions(value = "admin:category:list")
    @GetMapping("list")
    public BaseRespVo categoryList() {
        List<Category> categoryList = categoryService.list();
        return BaseRespVo.ok(categoryList);
    }

    @GetMapping("l1")
    public BaseRespVo getLevelOneCategory() {
        List<CategoryOneVO> lists = categoryService.queryLevelOne();
        return BaseRespVo.ok(lists);
    }

    @RequiresPermissions(value = "admin:category:create")
    @PostMapping("create")
    public BaseRespVo addCategory(@RequestBody Category category) {

        categoryService.addCategory(category);
        return BaseRespVo.ok();
    }

    @RequiresPermissions(value = "admin:category:delete")
    @PostMapping("delete")
    public BaseRespVo deleteCategory(@RequestBody Category category) {
        categoryService.deleteCategory(category);
        return BaseRespVo.ok();
    }

    @RequiresPermissions(value = "admin:category:update")
    @PostMapping("update")
    public BaseRespVo updateCategory(@RequestBody Category category){
        categoryService.updateCategory(category);
        return BaseRespVo.ok();
    }
}