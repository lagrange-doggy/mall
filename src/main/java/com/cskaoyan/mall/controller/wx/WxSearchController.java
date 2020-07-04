package com.cskaoyan.mall.controller.wx;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Keyword;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.bean.vo.HistoryKeywordListVo;
import com.cskaoyan.mall.bean.vo.SearchIndexVo;
import com.cskaoyan.mall.service.HistoryService;
import com.cskaoyan.mall.service.KeywordService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("wx/search")
public class WxSearchController {
    @Autowired
    KeywordService keywordService;
    @Autowired
    HistoryService historyService;


    @GetMapping("index")
    public BaseRespVo searchIndex() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<String> historyKeywordList = null;
        if (user != null) {
            historyKeywordList= historyService.searchHistoryAllByUserId(user.getId());
        }
        List<Keyword> keywordList = keywordService.queryKeywordListInHotKeyWord();
        Keyword keyword =  keywordService.queryKeywordListInHotKeyWordAndFirst();
        SearchIndexVo searchIndexVo = new SearchIndexVo(keyword, keywordList, stringList2HistoryKeywordListVoList(historyKeywordList));
        return BaseRespVo.ok(searchIndexVo);
    }

    @GetMapping("helper")
    public BaseRespVo searchHelper(String keyword) {
        List<String> helperList = keywordService.searchKeyWord(keyword);
        return BaseRespVo.ok(helperList);
    }
    @PostMapping("clearhistory")
    public BaseRespVo searchClearhistory() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        historyService.searchClearhistoryByUserId(user.getId());
        return BaseRespVo.ok();
    }

    private List<HistoryKeywordListVo> stringList2HistoryKeywordListVoList(List<String> strings) {
        if (null == strings) {
            return null;
        }
        List<HistoryKeywordListVo> historyKeywordListVos = new ArrayList<>();
        for (String string : strings) {
            historyKeywordListVos.add(new HistoryKeywordListVo(string));
        }
        return historyKeywordListVos;
    }
}
