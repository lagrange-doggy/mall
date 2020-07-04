package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Issue;

import java.util.Map;

public interface IssueService {
    Map<String, Object> queryIssuePageList(Integer page, Integer limit,String question, String sort, String order);

    Issue addIssue(Issue issue);

    void deleteIssue(Issue issue);

    void updateIssue(Issue issue);
}