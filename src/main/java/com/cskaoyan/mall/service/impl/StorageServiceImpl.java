package com.cskaoyan.mall.service.impl;


import ch.qos.logback.core.util.FileUtil;
import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.vo.StorageCreateVo;
import com.cskaoyan.mall.mapper.StorageMapper;
import com.cskaoyan.mall.service.StorageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    StorageMapper storageMapper;

    @Value("${file.url}")
    String url;

    @Override
    public StorageCreateVo createStorage(MultipartFile file) {
        StorageCreateVo storageCreateVo = new StorageCreateVo();
        Storage storage = new Storage();
        storage.setType(file.getContentType().toString());
        String originalFilename = file.getOriginalFilename();
        storage.setName(originalFilename);
        storage.setSize((int) file.getSize());
        storage.setUpdateTime(new Date(System.currentTimeMillis()));
        storage.setAddTime(new Date(System.currentTimeMillis()));
        int index = originalFilename.lastIndexOf('.');
        String string = originalFilename.substring(index, originalFilename.length());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = uuid + string;
        System.out.println(fileName);
        storage.setKey(fileName);
        storage.setUrl("http://localhost:8083/pic/" + fileName);
        try {
            file.transferTo(new File(url + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        storageMapper.insertSelective(storage);
        return new StorageCreateVo(storage.getId(), storage.getKey(), storage.getName(), storage.getType(), storage.getSize(),
                storage.getUrl(), storage.getAddTime().toString(), storage.getUpdateTime().toString());
    }

    @Override
    public BaseData queryStorage(Integer page, Integer limit, String sort, String order, String name, String key) {
        StorageExample storageExample = new StorageExample();
        storageExample.setOrderByClause(sort + " " + order);
        //如果你当前的查询有条件，就增加criteria
        String nameSql = "%%";
        if (name != null) {
            nameSql = "%" + name + "%";
        }
        if (key != null && !Objects.equals(key, "")) {
            storageExample.createCriteria().andNameLike(nameSql).andKeyEqualTo(key);
        } else {
            storageExample.createCriteria().andNameIsNotNull().andNameLike(nameSql);
        }
        //执行查询之前使用分页
        PageHelper.startPage(page, limit);
        List<Storage> storages = storageMapper.selectByExample(storageExample);
        PageInfo<Storage> pageInfo = new PageInfo<>(storages);
        long total = pageInfo.getTotal();
//        List<AdminListVo> adminListVoList = new ArrayList<>();
//        for (Admin admin : admins) {
////            List<Integer> roleIds = new ArrayList<Integer>();
////            roleIds.add((int) admin.getRoleIds().charAt(1) - '0');
//            adminListVoList.add(new AdminListVo(admin.getId(), admin.getUsername(), admin.getAvatar(), MyUtilWmz.string2List(admin.getRoleIds())));
//        }
        return new BaseData(storages, total);
    }

    @Override
    public Storage updateStorageNameById(Integer id, String name) {
        storageMapper.updateStorageNameById(id, name);
        return storageMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteStorageById(Integer id, String url) {
        int index = url.lastIndexOf("/");
        String fileUrl = this.url + url.substring(index + 1);
        File file = new File(fileUrl);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileUrl + "不存在！");
            return;
        } else {
            System.out.println("删除文件成功:" + fileUrl);
            file.delete();
        }
        storageMapper.deleteByPrimaryKey(id);
    }
}
