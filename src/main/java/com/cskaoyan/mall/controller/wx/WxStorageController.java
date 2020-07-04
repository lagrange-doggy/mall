package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.vo.WxUploadVo;
import com.cskaoyan.mall.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("wx/storage")
public class WxStorageController {

    @Autowired
    StorageService storageService;

    @RequestMapping("upload")
    public BaseRespVo uploadStorage(@RequestParam("file") MultipartFile file) {
        WxUploadVo wxUploadVo = new WxUploadVo();
        wxUploadVo.setUrl(storageService.createStorage(file).getUrl());
        return BaseRespVo.ok(wxUploadVo);
    }
}
