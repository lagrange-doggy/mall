package com.cskaoyan.mall.controller.admin;

import com.cskaoyan.mall.bean.vo.BaseRespVo;
import com.cskaoyan.mall.bean.Issue;
import com.cskaoyan.mall.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/issue")
@CrossOrigin
public class IssueController {
    @Autowired
    private IssueService issueService;
    @GetMapping("list")
    public BaseRespVo issuePageList(@RequestParam("page") Integer page,
                                    @RequestParam("limit") Integer limit,
                                    @RequestParam(value = "question",required = false) String question,
                                    @RequestParam("sort") String sort,
                                    @RequestParam("order") String order){
        Map<String,Object> map=issueService.queryIssuePageList(page,limit,question,sort,order);
        return BaseRespVo.ok(map);

    }


    @PostMapping("create")
    public BaseRespVo addIssue(@RequestBody Issue issue){
        Issue issueRet=issueService.addIssue(issue);
        return BaseRespVo.ok(issueRet);
    }

    @PostMapping("delete")
    public BaseRespVo deleteIssue(@RequestBody Issue issue){
        issueService.deleteIssue(issue);
        return BaseRespVo.ok();
    }

    @PostMapping("update")
    public BaseRespVo updateIssue(@RequestBody Issue issue){
        issueService.updateIssue(issue);
        return BaseRespVo.ok();
    }

}