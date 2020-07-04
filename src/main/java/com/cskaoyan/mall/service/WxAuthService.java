package com.cskaoyan.mall.service;

import java.util.Map;

public interface WxAuthService {
    int updateUserLog(String username);

    Map getUserInfo(String username);
}
