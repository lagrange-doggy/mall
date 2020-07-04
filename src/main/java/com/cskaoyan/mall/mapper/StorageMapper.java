package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Storage;
import com.cskaoyan.mall.bean.StorageExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface StorageMapper {
    long countByExample(StorageExample example);

    int deleteByExample(StorageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Storage record);

    int insertSelective(Storage record);

    List<Storage> selectByExample(StorageExample example);

    Storage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Storage record, @Param("example") StorageExample example);

    int updateByExample(@Param("record") Storage record, @Param("example") StorageExample example);

    int updateByPrimaryKeySelective(Storage record);

    int updateByPrimaryKey(Storage record);

    @Update("update cskaoyanmall_storage set `name` = #{name} ,`update_time` = NOW( ) where `id` = #{id}")
    void updateStorageNameById(Integer id, String name);
}