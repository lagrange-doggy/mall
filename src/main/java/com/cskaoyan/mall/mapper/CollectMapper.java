package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Collect;
import com.cskaoyan.mall.bean.CollectExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CollectMapper {
    long countByExample(CollectExample example);

    int deleteByExample(CollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    List<Collect> selectByExample(CollectExample example);

    Collect selectByPrimaryKey(Integer id);

    Collect selectByValueIdExists(@Param("valueId") Integer valueId);

    Collect selectByValueId(@Param("valueId") Integer valueId);

    int updateByExampleSelective(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByExample(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    void updateByValueId(Collect collect);

    @Select("select id from cskaoyanmall_collect where deleted =0 and type = 0 and value_id = #{id} and user_id = #{userId}")
    Integer queryHasCollectByTypeAndUserId(@Param("userId") int userId, @Param("id") int id);
}