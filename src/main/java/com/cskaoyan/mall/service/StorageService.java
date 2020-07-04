package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Storage;
import com.cskaoyan.mall.bean.vo.StorageCreateVo;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    StorageCreateVo createStorage(MultipartFile file);

    BaseData queryStorage(Integer page, Integer limit, String sort, String order, String name,String key);

    Storage updateStorageNameById(Integer id, String name);

    void deleteStorageById(Integer id, String url);
}
