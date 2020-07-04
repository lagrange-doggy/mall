package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Keyword;

import java.util.List;
import java.util.Map;

public interface KeywordService {
    Map<String, Object> queryKeywordPageList(Integer page, Integer limit, String keyword, String url, String sort, String order);

    Keyword addKeyword(Keyword keyword);

    void deleteKeyword(Keyword keyword);

    void updateKeyword(Keyword keyword);

    List<Keyword> queryKeywordListInHotKeyWord();

    Keyword queryKeywordListInHotKeyWordAndFirst();

    List<String> searchKeyWord(String keyword);




}