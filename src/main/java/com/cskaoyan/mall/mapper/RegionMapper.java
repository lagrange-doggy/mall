package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Region;
import com.cskaoyan.mall.bean.RegionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

public interface RegionMapper {
    long countByExample(RegionExample example);

    int deleteByExample(RegionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Region record);

    int insertSelective(Region record);

    List<Region> selectByExample(RegionExample example);

    Region selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Region record, @Param("example") RegionExample example);

    int updateByExample(@Param("record") Region record, @Param("example") RegionExample example);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);

    @Select("select name from cskaoyanmall_region where id = #{id}")
    String selectById(@Param("id") Integer id);

    @Select("select `id`,`pid`,`name`,`type`,`code` from cskaoyanmall_region where pid = #{pid}")
    List<Region> selectByPid(@Param("pid") Integer pid);

}