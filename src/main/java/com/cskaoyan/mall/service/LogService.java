package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.Log;

public interface LogService {

    BaseData queryUsers(Integer page, Integer limit, String sort, String order, String name);

    void insertLog(Log log);

}
