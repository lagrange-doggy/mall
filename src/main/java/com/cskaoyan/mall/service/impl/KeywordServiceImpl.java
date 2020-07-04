package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Keyword;
import com.cskaoyan.mall.config.MyException;
import com.cskaoyan.mall.constants.MallConstant;
import com.cskaoyan.mall.mapper.KeywordMapper;
import com.cskaoyan.mall.service.KeywordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private KeywordMapper keywordMapper;

    @Override
    public Map<String, Object> queryKeywordPageList(Integer page, Integer limit, String keyword, String url, String sort, String order) {

        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(page, limit);
        List<Keyword> Keywords = keywordMapper.queryKeywordPageList(keyword, url, sort, order);
        PageInfo pageInfo = new PageInfo(Keywords);
        long total = pageInfo.getTotal();
        map.put("total", total);
        map.put("items", Keywords);
        return map;
    }

    @Override
    public Keyword addKeyword(Keyword keyword) {
        keyword.setDeleted(false);
        keyword.setAddTime(new Date());
        keyword.setUpdateTime(new Date());
        keyword.setSortOrder(0);
        try {
            keywordMapper.insert(keyword);
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                throw new MyException(605, MallConstant.KEYWORD_ADD_ERROR);
            }
            throw new MyException(605, MallConstant.UNKNOWN_ERROR);
        }
        return keyword;
    }

    @Override
    public void deleteKeyword(Keyword keyword) {
        keyword.setDeleted(true);
        keyword.setUpdateTime(new Date());
        keywordMapper.updateByPrimaryKeySelective(keyword);
    }

    @Override
    public void updateKeyword(Keyword keyword) {
        keywordMapper.updateByPrimaryKeySelective(keyword);
    }

    @Override
    public List<Keyword> queryKeywordListInHotKeyWord() {
        return keywordMapper.queryKeywordListInHotKeyWord();
    }

    @Override
    public Keyword queryKeywordListInHotKeyWordAndFirst() {
        return keywordMapper.queryKeywordListInHotKeyWordAndFirst();
    }

    @Override
    public List<String> searchKeyWord(String keyword) {
        return keywordMapper.searchKeyWord(keyword);
    }


}