package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Footprint;
import com.cskaoyan.mall.bean.FootprintExample;
import com.cskaoyan.mall.bean.vo.FootprintVo;
import com.cskaoyan.mall.bean.vo.WxFootprintListVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FootprintMapper {
    long countByExample(FootprintExample example);

    int deleteByExample(FootprintExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Footprint record);

    int insertSelective(Footprint record);

    List<Footprint> selectByExample(FootprintExample example);

    Footprint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Footprint record, @Param("example") FootprintExample example);

    int updateByExample(@Param("record") Footprint record, @Param("example") FootprintExample example);

    int updateByPrimaryKeySelective(Footprint record);

    int updateByPrimaryKey(Footprint record);

    List<FootprintVo> queryFootsOnWx(@Param("userId") Integer userId);

    @Update("update cskaoyanmall_footprint set `deleted` = 1 ,`update_time` = NOW( ) where `id` = #{id} ")
    void deleteFootprint(@Param("id") Integer id);

    @Select("select id from cskaoyanmall_footprint where user_id = #{userId} and goods_id = #{goodsId} ")
    Integer selectFootprintByUserIdAndGoodsId(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    @Insert("insert into cskaoyanmall_footprint (user_id, goods_id, add_time, update_time, deleted) values (#{userId} ,#{goodsId} ,NOW(),NOW(),0)")
    void insertFootprint(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    @Update("update cskaoyanmall_footprint set update_time = NOW(), deleted = 0 where user_id = #{userId} and goods_id = #{goodsId} ")
    void updateFootprint(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);
}