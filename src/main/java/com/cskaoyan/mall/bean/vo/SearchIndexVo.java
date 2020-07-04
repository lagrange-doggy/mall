package com.cskaoyan.mall.bean.vo;

import com.cskaoyan.mall.bean.History;
import com.cskaoyan.mall.bean.Keyword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchIndexVo {

    private Keyword defaultKeyword;
    private List<Keyword> hotKeywordList;
    private List<HistoryKeywordListVo> historyKeywordList;

//    public SearchIndexVo(Keyword defaultKeyword, List<Keyword> hotKeywordList, List<String> historyKeywordList) {
//        this.defaultKeyword = defaultKeyword;
//        this.hotKeywordList = hotKeywordList;
//        this.historyKeywordList = new HistoryKeywordListVo();
//        this.historyKeywordList.setHistoryKeywordList(historyKeywordList);
//    }
}
