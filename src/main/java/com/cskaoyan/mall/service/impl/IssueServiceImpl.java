package com.cskaoyan.mall.service.impl;

import com.cskaoyan.mall.bean.Issue;
import com.cskaoyan.mall.mapper.IssueMapper;
import com.cskaoyan.mall.service.IssueService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueMapper issueMapper;
    @Override
    public Map<String, Object> queryIssuePageList(Integer page, Integer limit, String question, String sort, String order) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(page, limit);
        List<Issue> Issues = issueMapper.queryIssuePageList(question, sort, order);
        PageInfo pageInfo = new PageInfo(Issues);
        long total = pageInfo.getTotal();
        map.put("total", total);
        map.put("items", Issues);
        return map;
    }

    @Override
    public Issue addIssue(Issue issue) {
        issue.setAddTime(new Date());
        issue.setDeleted(false);
        issue.setUpdateTime(new Date());
        issueMapper.insert(issue);
        return issue;
    }

    @Override
    public void deleteIssue(Issue issue) {
        issue.setUpdateTime(new Date());
        issue.setDeleted(true);
        issueMapper.updateByPrimaryKeySelective(issue);
    }

    @Override
    public void updateIssue(Issue issue) {
        issue.setUpdateTime(new Date());
        issueMapper.updateByPrimaryKeySelective(issue);
    }
}