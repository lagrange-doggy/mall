package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Storage;
import com.cskaoyan.mall.bean.vo.StorageCreateVo;
import com.cskaoyan.mall.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("admin/storage")
public class StorageController {
    @Autowired
    StorageService storageService;

    @RequestMapping("create")
    public BaseRespVo storageCreate(@RequestParam("file") MultipartFile file) throws IOException {
        StorageCreateVo storageCreateVo = storageService.createStorage(file);
        return BaseRespVo.ok(storageCreateVo);
    }

    @RequestMapping("list")
    @ResponseBody
    public BaseRespVo storageList(Integer page,Integer limit,String sort,String order , String name , String key) {
        BaseData baseData = storageService.queryStorage(page, limit, sort, order,name,key);
        return BaseRespVo.ok(baseData);
    }

    @RequestMapping("update")
    @ResponseBody
    public BaseRespVo storageUpdate(@RequestBody Storage storage) {
        Storage baseData = storageService.updateStorageNameById(storage.getId(),storage.getName());
        return BaseRespVo.ok(baseData);
    }

    @RequestMapping("delete")
    @ResponseBody
    public BaseRespVo storageDelete(@RequestBody Storage storage) {
        storageService.deleteStorageById(storage.getId(),storage.getUrl());
        return BaseRespVo.ok();
    }
}
