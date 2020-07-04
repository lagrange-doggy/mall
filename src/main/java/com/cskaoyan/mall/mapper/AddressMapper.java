package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Address;
import com.cskaoyan.mall.bean.AddressExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AddressMapper {
    long countByExample(AddressExample example);

    int deleteByExample(AddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    int insertSelective(Address record);

    List<Address> selectByExample(AddressExample example);

    Address selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Address record, @Param("example") AddressExample example);

    int updateByExample(@Param("record") Address record, @Param("example") AddressExample example);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    List<Address> selectByUserId(Integer userId);

    void updateIsDefault(@Param("id") Integer id);

    @Update("update cskaoyanmall_address set is_default = 0 where user_id = #{userId}")
    void updateIsDefaultByUserId(Integer userId);

    @Update("update cskaoyanmall_address set is_default = 1 where id = #{id}")
    void updateIsDefaultById(Integer id);
}