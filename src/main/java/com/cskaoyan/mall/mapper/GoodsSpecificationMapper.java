package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.GoodsSpecification;
import com.cskaoyan.mall.bean.GoodsSpecificationExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsSpecificationMapper {
    long countByExample(GoodsSpecificationExample example);

    int deleteByExample(GoodsSpecificationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsSpecification record);//gz

    int insertSelective(GoodsSpecification record);

    List<GoodsSpecification> selectByExample(GoodsSpecificationExample example);

    GoodsSpecification selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsSpecification record, @Param("example") GoodsSpecificationExample example);

    int updateByExample(@Param("record") GoodsSpecification record, @Param("example") GoodsSpecificationExample example);

    int updateByPrimaryKeySelective(GoodsSpecification record);

    int updateByPrimaryKey(GoodsSpecification record);

    int updateDeletedByGoodsId(Integer id); //gz

    int logicalDeleteSpecificationForGoodsUpdate(@Param("goodsId") Integer goodsId, @Param("ids") List<Integer> IdsList);
    //拿names
    @Select("select distinct specification from cskaoyanmall_goods_specification where goods_id = #{id}" )
    String[] queryNameOfSpecByGoodsId(Integer id);


    @Select("select distinct specification from cskaoyanmall_goods_specification where goods_id = #{id} and deleted = false" )
    List<String> selectSpecificationNames(Integer goodsid);
}