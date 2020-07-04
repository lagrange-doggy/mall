package com.cskaoyan.mall.mapper;


import com.cskaoyan.mall.bean.Keyword;
import com.cskaoyan.mall.bean.KeywordExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

public interface KeywordMapper {
    int countByExample(KeywordExample example);

    int deleteByExample(KeywordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Keyword record);

    int insertSelective(Keyword record);

    List<Keyword> selectByExample(KeywordExample example);

    Keyword selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Keyword record, @Param("example") KeywordExample example);

    int updateByExample(@Param("record") Keyword record, @Param("example") KeywordExample example);

    int updateByPrimaryKeySelective(Keyword record);

    int updateByPrimaryKey(Keyword record);

    List<Keyword> queryKeywordPageList(@Param("keyword") String keyword, @Param("url") String url, @Param("sortWay") String sortWay, @Param("orderWay") String orderWay);

    @Select("select * from cskaoyanmall_keyword where `is_hot` != 0 and `deleted` = 0")
    List<Keyword> queryKeywordListInHotKeyWord();

    @Select("SELECT * FROM cskaoyanmall_keyword WHERE sort_order = ( SELECT MIN( sort_order ) FROM cskaoyanmall_keyword ) and `deleted` = 0")
    Keyword queryKeywordListInHotKeyWordAndFirst();

    @Select("select `keyword` FROM cskaoyanmall_keyword where `keyword` like CONCAT('%',#{keyword} ,'%') and (`deleted` = 0 )")
    List<String> searchKeyWord(@Param("keyword") String keyword);


}
