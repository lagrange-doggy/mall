package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.History;
import com.cskaoyan.mall.bean.HistoryExample;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface HistoryMapper {
    long countByExample(HistoryExample example);

    int deleteByExample(HistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(History record);

    int insertSelective(History record);

    List<History> selectByExample(HistoryExample example);

    History selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") History record, @Param("example") HistoryExample example);

    int updateByExample(@Param("record") History record, @Param("example") HistoryExample example);

    int updateByPrimaryKeySelective(History record);

    int updateByPrimaryKey(History record);

    @Select("select `keyword` from cskaoyanmall_search_history where `user_id` = #{userId} and `deleted` = 0 ORDER BY update_time")
    List<String> searchHistoryAllByUserId(@Param("userId") Integer userId);

    @Update("UPDATE cskaoyanmall_search_history SET `deleted` = 1 WHERE `user_id` = #{id} ")
    void searchClearhistoryByUserId(@Param("id") Integer id);

    @Select("select id from cskaoyanmall_search_history where user_id = #{userId} and keyword = #{keyword} ")
    Integer selectHistoryByUserIdAndGoodsId(@Param("userId") Integer userId, @Param("keyword") String keyword);

    @Insert("insert into cskaoyanmall_search_history (user_id, keyword, `from`, add_time, update_time, deleted) values (#{userId} ,#{keyword}, \"wx\",NOW(),NOW(),0)")
    void insertHistory(@Param("userId") Integer userId, @Param("keyword") String keyword);

    @Update("UPDATE cskaoyanmall_search_history SET `deleted` = 0 ,update_time = NOW() WHERE `user_id` = #{userId}  and keyword =#{keyword}  ")
    void updateHistoryt(@Param("userId") Integer userId, @Param("keyword") String keyword);
}