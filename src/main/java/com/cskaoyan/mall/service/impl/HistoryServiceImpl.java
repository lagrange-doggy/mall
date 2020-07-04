package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.BaseData;
import com.cskaoyan.mall.bean.History;
import com.cskaoyan.mall.bean.HistoryExample;
import com.cskaoyan.mall.mapper.HistoryMapper;
import com.cskaoyan.mall.service.HistoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    HistoryMapper historyMapper;

    @Override
    public BaseData searchHistory(Integer page, Integer limit, Integer userId, String keyword, String sort, String order) {
        HistoryExample historyExample = new HistoryExample();
        historyExample.setOrderByClause(sort + " " + order);
        //仅增加了模糊查询
        if(userId != null && keyword == null){
            historyExample.createCriteria().andUserIdEqualTo(userId);
        }
        if(userId == null && keyword != null && keyword != ""){
            historyExample.createCriteria().andKeywordLike("%"+keyword+"%");
        }
        if(userId != null && keyword != null && keyword != ""){
            historyExample.createCriteria().andUserIdEqualTo(userId).andKeywordLike("%"+keyword+"%");
        }

        PageHelper.startPage(page,limit);
        List<History> history = historyMapper.selectByExample(historyExample);
        PageInfo<History> pageInfo = new PageInfo<>(history);
        long total = pageInfo.getTotal();
        return new BaseData(history, total);
    }

    @Override
    public List<String> searchHistoryAllByUserId(Integer userId) {
        return historyMapper.searchHistoryAllByUserId(userId);
    }

    @Override
    public void searchClearhistoryByUserId(Integer id) {
        historyMapper.searchClearhistoryByUserId(id);
    }

    @Override
    public void insertHistory(Integer userId, String keyword) {
        if (historyMapper.selectHistoryByUserIdAndGoodsId(userId, keyword) == null) {
            historyMapper.insertHistory(userId, keyword);
        } else {
            historyMapper.updateHistoryt(userId, keyword);
        }
    }
}
