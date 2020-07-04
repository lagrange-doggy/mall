package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Keyword;
import com.cskaoyan.mall.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/keyword")
@CrossOrigin
public class KeywordController {

    @Autowired
    private KeywordService keywordService;
    @GetMapping("list")
    public BaseRespVo keywordPageList(@RequestParam("page") Integer page,
                                      @RequestParam("limit") Integer limit,
                                      @RequestParam(value = "keyword",required = false) String keyword,
                                      @RequestParam(value = "url",required =false) String url,
                                      @RequestParam("sort") String sort,
                                      @RequestParam("order") String order){
        Map<String,Object> map=keywordService.queryKeywordPageList(page,limit,keyword,url,sort,order);
        return BaseRespVo.ok(map);

    }


    @PostMapping("create")
    public BaseRespVo addIssue(@RequestBody Keyword keyword){
        Keyword KeywordRet=keywordService.addKeyword(keyword);
        return BaseRespVo.ok(KeywordRet);
    }

    @PostMapping("delete")
    public BaseRespVo deleteIssue(@RequestBody Keyword keyword){
        keywordService.deleteKeyword(keyword);
        return BaseRespVo.ok();
    }

    @PostMapping("update")
    public BaseRespVo updateIssue(@RequestBody Keyword keyword){
        keywordService.updateKeyword(keyword);
        return BaseRespVo.ok();
    }
}