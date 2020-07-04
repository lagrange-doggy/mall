package com.cskaoyan.mall.mapper;


import com.cskaoyan.mall.bean.Issue;
import com.cskaoyan.mall.bean.IssueExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface IssueMapper {
    int countByExample(IssueExample example);

    int deleteByExample(IssueExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Issue record);

    int insertSelective(Issue record);

    List<Issue> selectByExample(IssueExample example);

    Issue selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Issue record, @Param("example") IssueExample example);

    int updateByExample(@Param("record") Issue record, @Param("example") IssueExample example);

    int updateByPrimaryKeySelective(Issue record);

    int updateByPrimaryKey(Issue record);

    List<Issue> queryIssuePageList(@Param("question") String question, @Param("sortWay") String sortWay, @Param("orderWay") String orderWay);
}