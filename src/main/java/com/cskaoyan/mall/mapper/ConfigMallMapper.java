package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.ConfigMall;
import com.cskaoyan.mall.bean.ConfigMallExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConfigMallMapper {
    long countByExample(ConfigMallExample example);

    int deleteByExample(ConfigMallExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConfigMall record);

    int insertSelective(ConfigMall record);

    List<ConfigMall> selectByExample(ConfigMallExample example);

    ConfigMall selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConfigMall record, @Param("example") ConfigMallExample example);

    int updateByExample(@Param("record") ConfigMall record, @Param("example") ConfigMallExample example);

    int updateByPrimaryKeySelective(ConfigMall record);

    int updateByPrimaryKey(ConfigMall record);
}