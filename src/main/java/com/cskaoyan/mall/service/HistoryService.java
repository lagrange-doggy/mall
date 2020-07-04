package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.BaseData;

import java.util.List;

public interface HistoryService {
    BaseData searchHistory(Integer page, Integer limit, Integer userId, String keyword, String sort, String order);

    List<String> searchHistoryAllByUserId(Integer userId);

    void searchClearhistoryByUserId(Integer id);

    void insertHistory(Integer userId, String keyword);
}
